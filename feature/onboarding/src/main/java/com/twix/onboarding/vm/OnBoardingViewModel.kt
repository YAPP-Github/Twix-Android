package com.twix.onboarding.vm

import androidx.lifecycle.viewModelScope
import com.twix.domain.repository.OnBoardingRepository
import com.twix.onboarding.model.OnBoardingIntent
import com.twix.onboarding.model.OnBoardingSideEffect
import com.twix.onboarding.model.OnBoardingUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class OnBoardingViewModel(
    private val onBoardingRepository: OnBoardingRepository,
) : BaseViewModel<OnBoardingUiState, OnBoardingIntent, OnBoardingSideEffect>(OnBoardingUiState()) {
    override suspend fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            is OnBoardingIntent.WriteNickName -> {
                reduceNickName(intent.value)
            }

            OnBoardingIntent.SubmitNickName -> {
                handleSubmitNickname()
            }
        }
    }

    private fun reduceNickName(value: String) {
        reduce { updateNickName(value) }
    }

    private fun handleSubmitNickname() {
        val sideEffect =
            if (uiState.value.isValidNickName) {
                OnBoardingSideEffect.ProfileSetting.NavigateToNext
            } else {
                OnBoardingSideEffect.ProfileSetting.ShowInvalidNickNameToast
            }

        viewModelScope.launch {
            emitSideEffect(sideEffect)
        }
    }
}
