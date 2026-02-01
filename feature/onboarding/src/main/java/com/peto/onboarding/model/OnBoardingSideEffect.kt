package com.peto.onboarding.model

import com.twix.ui.base.SideEffect

sealed interface OnBoardingSideEffect : SideEffect {
    data class ShowInvalidNickNameToast(
        val message: String,
    ) : OnBoardingSideEffect
}
