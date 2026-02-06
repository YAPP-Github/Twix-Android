package com.twix.onboarding.model

import com.twix.ui.base.SideEffect

sealed interface OnBoardingSideEffect : SideEffect {
    sealed interface ProfileSetting : OnBoardingSideEffect {
        data object ShowInvalidNickNameToast : ProfileSetting

        data object NavigateToDDaySetting : CoupleConnection

        data object NavigateToHome : InviteCode
    }

    sealed interface CoupleConnection : OnBoardingSideEffect

    sealed interface InviteCode : OnBoardingSideEffect {
        data object ShowCopyInviteCodeSuccessToast : InviteCode

        data object NavigateToNext : CoupleConnection
    }

    sealed interface DDaySetting : OnBoardingSideEffect
}
