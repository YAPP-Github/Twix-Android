package com.twix.login.model

import com.twix.domain.model.OnboardingStatus
import com.twix.ui.base.SideEffect

sealed interface LoginSideEffect : SideEffect {
    data object NavigateToHome : LoginSideEffect

    data class NavigateToOnBoarding(
        val status: OnboardingStatus,
    ) : LoginSideEffect

    data object ShowLoginFailToast : LoginSideEffect

    data object ShowFetchOnBoardingStatusFailToast : LoginSideEffect
}
