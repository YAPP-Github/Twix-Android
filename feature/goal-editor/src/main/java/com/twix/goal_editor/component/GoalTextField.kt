package com.twix.goal_editor.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.components.text_field.UnderlineTextField

@Composable
fun GoalTextField(
    value: String,
    onCommitTitle: (String) -> Unit,
) {
    var internalValue by rememberSaveable(value) { mutableStateOf(value) }
    // 초기에 무의미하게 commit 되는 것을 방지하는 상태 변수
    var wasFocused by remember { mutableStateOf(false) }

    UnderlineTextField(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (wasFocused && !state.isFocused) {
                        onCommitTitle(internalValue.trim())
                    }
                    wasFocused = state.isFocused
                },
        value = internalValue,
        placeHolder = stringResource(R.string.goal_editor_text_field_placeholder),
        onValueChange = { internalValue = it },
    )
}
