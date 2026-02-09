package com.twix.designsystem.keyboard

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity

// TODO : core:ui 모듈로 이동

/**
 * 현재 소프트 키보드(IME)의 열림/닫힘 상태를 [Keyboard] 값으로 제공하는 Composable 유틸 함수.
 *
 * [WindowInsets.ime] 의 bottom inset 값을 사용하여
 * 키보드가 화면을 밀어 올리고 있는지 여부를 감지합니다.
 *
 * - inset > 0  → [Keyboard.Opened]
 * - inset == 0 → [Keyboard.Closed]
 *
 * @return 현재 키보드 상태를 나타내는 [State]<[Keyboard]>
 */
@Composable
fun keyboardAsState(): State<Keyboard> {
    val density = LocalDensity.current
    val keyboard =
        if (WindowInsets.ime.getBottom(density) > 0) {
            Keyboard.Opened
        } else {
            Keyboard.Closed
        }
    return rememberUpdatedState(keyboard)
}
