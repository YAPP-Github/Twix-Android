package com.twix.onboarding.model

import com.twix.ui.base.SideEffect

sealed interface OnBoardingSideEffect : SideEffect {
    sealed interface ProfileSetting : OnBoardingSideEffect {
        data object ShowInvalidNickNameToast : ProfileSetting

        data object NavigateToNext : ProfileSetting
    }

    sealed interface CoupleConnection : OnBoardingSideEffect

    sealed interface DDaySetting : OnBoardingSideEffect
}
