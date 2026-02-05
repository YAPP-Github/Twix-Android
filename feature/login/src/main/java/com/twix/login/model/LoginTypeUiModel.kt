package com.twix.login.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class LoginTypeUiModel(
    val logo: ImageVector,
    val title: String,
    val background: Color,
    val border: Color,
    val textColor: Color,
)
