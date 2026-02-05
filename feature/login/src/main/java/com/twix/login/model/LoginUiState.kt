package com.twix.login.model

import com.twix.ui.base.State

data class LoginUiState(
    val isLoggedIn: Boolean = false,
) : State
