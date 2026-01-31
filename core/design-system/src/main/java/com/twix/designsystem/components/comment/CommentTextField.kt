package com.twix.designsystem.components.comment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.components.comment.model.CommentUiModel
import com.twix.designsystem.keyboard.Keyboard
import com.twix.designsystem.keyboard.keyboardAsState
import com.twix.designsystem.theme.TwixTheme
import kotlinx.coroutines.android.awaitFrame

private val CIRCLE_PADDING_START: Dp = 50.dp
private val CIRCLE_SIZE: Dp = 64.dp
private val CIRCLE_GAP: Dp = CIRCLE_PADDING_START - CIRCLE_SIZE

@Composable
fun CommentTextField(
    uiModel: CommentUiModel,
    onCommentChanged: (TextFieldValue) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
    val placeholder = stringResource(R.string.comment_text_field_placeholder)

    val keyboardVisibility by keyboardAsState()

    LaunchedEffect(keyboardVisibility) {
        when (keyboardVisibility) {
            Keyboard.Opened -> Unit
            Keyboard.Closed -> {
                focusManager.clearFocus()
                onFocusChanged(false)
            }
        }
    }

    LaunchedEffect(uiModel.isFocused) {
        if (uiModel.isFocused) {
            focusRequester.requestFocus()
            awaitFrame()
            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }

    Box(
        modifier =
            modifier
                .clickable(
                    // TODO : noClickableRipple 로 수정
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { focusRequester.requestFocus() },
                ),
    ) {
        BasicTextField(
            value = uiModel.comment,
            onValueChange = { newValue -> onCommentChanged(newValue) },
            modifier =
                Modifier
                    .alpha(0f)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            singleLine = true,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(CIRCLE_GAP),
            modifier =
                Modifier.drawWithCache {
                    val radius = size.height / 2
                    val paddingStart = CIRCLE_PADDING_START.toPx()

                    onDrawBehind {
                        repeat(CommentUiModel.COMMENT_COUNT) { index ->
                            val cx = radius + index * paddingStart

                            drawCircle(
                                color = Color.Black,
                                radius = radius,
                                center = Offset(cx, radius),
                                style = Stroke(2.dp.toPx()),
                            )
                        }
                    }
                },
        ) {
            repeat(CommentUiModel.COMMENT_COUNT) { index ->
                val char =
                    if (uiModel.hidePlaceholder) {
                        uiModel.comment.text
                            .getOrNull(index)
                            ?.toString()
                    } else {
                        placeholder.getOrNull(index)?.toString()
                    }.orEmpty()

                CommentCircle(
                    text = char,
                    showPlaceholder = !uiModel.hidePlaceholder,
                    showCursor = uiModel.showCursor(index),
                    modifier =
                        Modifier.clickable(
                            indication = null,
                            interactionSource = interactionSource,
                        ) {
                            focusRequester.requestFocus()
                            onCommentChanged(uiModel.comment.copy(selection = TextRange(uiModel.comment.text.length)))
                        },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CommentTextFieldPreview() {
    TwixTheme {
        var text by remember { mutableStateOf(TextFieldValue("")) }
        var isFocused by remember { mutableStateOf(false) }
        CommentTextField(
            uiModel = CommentUiModel(text, isFocused),
            onCommentChanged = { text = it },
            onFocusChanged = { isFocused = it },
        )
    }
}
