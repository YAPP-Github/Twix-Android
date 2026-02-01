package com.peto.onboarding.vm

import com.peto.onboarding.model.OnBoardingIntent
import com.peto.onboarding.model.OnBoardingSideEffect
import com.peto.onboarding.model.OnBoardingUiState
import com.twix.domain.repository.OnBoardingRepository
import com.twix.ui.base.BaseViewModel

class OnBoardingViewModel(
    private val onBoardingRepository: OnBoardingRepository,
) : BaseViewModel<OnBoardingUiState, OnBoardingIntent, OnBoardingSideEffect>(OnBoardingUiState()) {
    override suspend fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            is OnBoardingIntent.WriteNickName -> {
                reduceNickName(intent.value)
            }
        }
    }

    private fun reduceNickName(value: String) {
        reduce { updateNickName(value) }
    }
}
