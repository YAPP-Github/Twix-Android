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
    init {
        fetchMyInviteCode()
    }

    private fun fetchMyInviteCode() {
        viewModelScope.launch {
            val result = onBoardingRepository.fetchInviteCode()
            reduce { updateMyInviteCode(result.value) }
        }
    }

    override suspend fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            is OnBoardingIntent.WriteNickName -> reduceNickName(intent.value)
            OnBoardingIntent.SubmitNickName -> handleSubmitNickname()
            is OnBoardingIntent.WriteInviteCode -> reduceInviteCode(intent.value)
            OnBoardingIntent.ConnectCouple -> connectCouple()
            OnBoardingIntent.CopyInviteCode -> {
                emitSideEffect(OnBoardingSideEffect.InviteCode.ShowCopyInviteCodeSuccessToast)
            }
        }
    }

    private fun reduceNickName(value: String) {
        reduce { updateNickName(value) }
    }

    private fun handleSubmitNickname() {
        val sideEffect =
            if (currentState.isValidNickName) {
                OnBoardingSideEffect.ProfileSetting.NavigateToNext
            } else {
                OnBoardingSideEffect.ProfileSetting.ShowInvalidNickNameToast
            }

        viewModelScope.launch {
            emitSideEffect(sideEffect)
        }
    }

    private fun reduceInviteCode(value: String) {
        reduce { updatePartnerInviteCode(value) }
    }

    private fun connectCouple() {
        val currentUiState = currentState.inviteCode
        if (!currentUiState.isValid) return

        viewModelScope.launch {
            // TODO : 상대방이 이미 연결했을 때 에러처리 구현
            onBoardingRepository.coupleConnection(currentUiState.partnerInviteCode)
            emitSideEffect(OnBoardingSideEffect.CoupleConnection.NavigateToNext)
        }
    }
}
