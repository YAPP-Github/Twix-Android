package com.twix.login.model

import com.twix.ui.base.SideEffect

sealed interface LoginSideEffect : SideEffect {
    data object NavigateToHome : LoginSideEffect

    data object NavigateToOnBoarding : LoginSideEffect

    data object ShowLoginFailToast : LoginSideEffect
}
