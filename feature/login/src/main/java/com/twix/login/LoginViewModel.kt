package com.twix.login

import androidx.lifecycle.viewModelScope
import com.twix.domain.login.LoginResult
import com.twix.domain.model.OnboardingStatus
import com.twix.domain.repository.AuthRepository
import com.twix.domain.repository.OnBoardingRepository
import com.twix.login.model.LoginIntent
import com.twix.login.model.LoginSideEffect
import com.twix.login.model.LoginUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val onBoardingRepository: OnBoardingRepository,
) : BaseViewModel<LoginUiState, LoginIntent, LoginSideEffect>(LoginUiState()) {
    override suspend fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> login(intent.result)
        }
    }

    private fun login(result: LoginResult) {
        viewModelScope.launch {
            when (result) {
                is LoginResult.Success -> {
                    authRepository.login(result.idToken, result.type)
                    checkOnboardingStatus()
                }
                is LoginResult.Failure -> {
                    emitSideEffect(LoginSideEffect.ShowLoginFailToast)
                }
                LoginResult.Cancel -> Unit
            }
        }
    }

    private fun checkOnboardingStatus() {
        viewModelScope.launch {
            when (val status = onBoardingRepository.fetchOnboardingStatus()) {
                OnboardingStatus.COUPLE_CONNECTION,
                OnboardingStatus.PROFILE_SETUP,
                OnboardingStatus.ANNIVERSARY_SETUP,
                -> emitSideEffect(LoginSideEffect.NavigateToOnBoarding(status))
                OnboardingStatus.COMPLETED -> emitSideEffect(LoginSideEffect.NavigateToHome)
            }
        }
    }
}
