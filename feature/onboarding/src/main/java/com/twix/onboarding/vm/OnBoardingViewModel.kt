package com.twix.onboarding.vm

import androidx.lifecycle.viewModelScope
import com.twix.domain.model.OnboardingStatus
import com.twix.domain.repository.OnBoardingRepository
import com.twix.onboarding.model.OnBoardingIntent
import com.twix.onboarding.model.OnBoardingSideEffect
import com.twix.onboarding.model.OnBoardingUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class OnBoardingViewModel(
    private val onBoardingRepository: OnBoardingRepository,
) : BaseViewModel<OnBoardingUiState, OnBoardingIntent, OnBoardingSideEffect>(OnBoardingUiState()) {
    fun fetchMyInviteCode() {
        viewModelScope.launch {
            val result = onBoardingRepository.fetchInviteCode()
            reduce { updateMyInviteCode(result.value) }
        }
    }

    override suspend fun handleIntent(intent: OnBoardingIntent) {
        when (intent) {
            // 커플 연결 화면
            is OnBoardingIntent.WriteInviteCode -> reduceInviteCode(intent.value)
            OnBoardingIntent.ConnectCouple -> connectCouple()
            OnBoardingIntent.CopyInviteCode ->
                emitSideEffect(OnBoardingSideEffect.InviteCode.ShowCopyInviteCodeSuccessToast)

            // 프로필 설정 화면
            is OnBoardingIntent.WriteNickName -> reduceNickName(intent.value)
            OnBoardingIntent.SubmitNickName -> handleSubmitNickname()

            // 디데이 설정 화면
            is OnBoardingIntent.SelectDate -> reduceDday(intent.value)
            OnBoardingIntent.SubmitDday -> anniversarySetup()
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
            emitSideEffect(OnBoardingSideEffect.InviteCode.NavigateToNext)
        }
    }

    private fun reduceNickName(value: String) {
        reduce { updateNickName(value) }
    }

    private fun handleSubmitNickname() {
        if (currentState.isValidNickName) {
            profileSetup()
        } else {
            viewModelScope.launch {
                emitSideEffect(OnBoardingSideEffect.ProfileSetting.ShowInvalidNickNameToast)
            }
        }
    }

    private fun profileSetup() {
        viewModelScope.launch {
            onBoardingRepository.profileSetup(currentState.profile.nickname)
            fetchOnboardingStatus()
        }
    }

    private fun fetchOnboardingStatus() {
        viewModelScope.launch {
            when (onBoardingRepository.fetchOnboardingStatus()) {
                OnboardingStatus.ANNIVERSARY_SETUP -> {
                    emitSideEffect(OnBoardingSideEffect.ProfileSetting.NavigateToDDaySetting)
                }

                OnboardingStatus.COMPLETED -> {
                    emitSideEffect(OnBoardingSideEffect.ProfileSetting.NavigateToHome)
                }

                else -> Unit
            }
        }
    }

    private fun reduceDday(value: LocalDate) {
        reduce { updateDday(value) }
    }

    private fun anniversarySetup() {
        viewModelScope.launch {
            onBoardingRepository.anniversarySetup(currentState.dDay.anniversaryDate.toString())
            emitSideEffect(OnBoardingSideEffect.DdaySetting.NavigateToHome)
        }
    }
}
