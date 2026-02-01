package com.twix.designsystem.components.comment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle

@Composable
internal fun CommentCircle(
    text: String,
    showPlaceholder: Boolean,
    showCursor: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            modifier
                .size(64.dp)
                .background(color = CommonColor.White, shape = CircleShape),
    ) {
        AppText(
            text = text,
            style = AppTextStyle.H1,
            color = if (showPlaceholder) GrayColor.C200 else GrayColor.C500,
        )

        if (showCursor) CursorBar()
    }
}

@Composable
private fun CursorBar() {
    Box(
        modifier =
            Modifier
                .width(2.dp)
                .height(28.dp)
                .background(GrayColor.C500),
    )
}

@Preview
@Composable
private fun CommentCirclePreview() {
    TwixTheme {
        CommentCircle(text = "1", showPlaceholder = false, showCursor = false)
    }
}
