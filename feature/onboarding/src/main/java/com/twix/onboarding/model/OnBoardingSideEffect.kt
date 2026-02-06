package com.twix.onboarding.model

import com.twix.ui.base.SideEffect

sealed interface OnBoardingSideEffect : SideEffect {
    sealed interface ProfileSetting : OnBoardingSideEffect {
        data object ShowInvalidNickNameToast : ProfileSetting

        data object NavigateToDDaySetting : ProfileSetting

        data object NavigateToHome : ProfileSetting
    }

    sealed interface CoupleConnection : OnBoardingSideEffect

    sealed interface InviteCode : OnBoardingSideEffect {
        data object ShowCopyInviteCodeSuccessToast : InviteCode

        data object NavigateToNext : InviteCode
    }

    sealed interface DdaySetting : OnBoardingSideEffect {
        data object NavigateToHome : DdaySetting
    }
}
