package com.twix.login

import androidx.lifecycle.viewModelScope
import com.twix.domain.login.LoginResult
import com.twix.domain.repository.AuthRepository
import com.twix.login.model.LoginIntent
import com.twix.login.model.LoginSideEffect
import com.twix.login.model.LoginUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
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
                    emitSideEffect(LoginSideEffect.NavigateToHome)
                }
                is LoginResult.Failure -> {
                    emitSideEffect(LoginSideEffect.ShowLoginFailToast)
                }
                LoginResult.Cancel -> Unit
            }
        }
    }
}
