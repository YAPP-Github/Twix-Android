package com.twix.goal_editor.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.theme.GrayColor
import com.twix.ui.extension.noRippleClickable

@Composable
fun EmojiPicker(
    emojiId: Long,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .clip(CircleShape)
                .background(GrayColor.C050)
                .border(1.dp, GrayColor.C300, CircleShape)
                .padding(26.dp)
                .noRippleClickable(onClick = onClick),
    ) {
        Image(
            painter =
                if (emojiId == -1L) {
                    painterResource(R.drawable.ic_emoji_add)
                } else {
                    painterResource(R.drawable.ic_emoji_add)
                },
            contentDescription = "emoji",
            modifier =
                Modifier
                    .size(56.dp),
        )
    }
}
