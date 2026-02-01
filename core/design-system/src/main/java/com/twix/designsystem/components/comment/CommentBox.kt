package com.twix.designsystem.components.comment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.components.comment.model.CommentUiModel
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.domain.model.enums.AppTextStyle

@Composable
fun CommentBox(
    uiModel: CommentUiModel,
    onCommentChanged: (TextFieldValue) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    onGuideTextPositioned: (Rect) -> Unit,
    onTextFieldPositioned: (Rect) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (uiModel.isFocused) {
            AppText(
                text = stringResource(R.string.comment_condition_guide),
                style = AppTextStyle.B2,
                color = GrayColor.C100,
                modifier =
                    Modifier.onGloballyPositioned {
                        onGuideTextPositioned(it.boundsInRoot())
                    },
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
        CommentTextField(
            uiModel = uiModel,
            onCommentChanged = onCommentChanged,
            onFocusChanged = onFocusChanged,
            onPositioned = onTextFieldPositioned,
            modifier =
                Modifier
                    .padding(bottom = 20.dp),
        )
    }
}
