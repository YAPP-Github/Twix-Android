package com.twix.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("AppTypography가 제공되지 않음")
}

@Composable
fun TwixTheme(
    content: @Composable () -> Unit
) {
    val typography = remember { provideAppTypography() }

    CompositionLocalProvider(
        LocalAppTypography provides typography,
    ) {
        content()
    }
}