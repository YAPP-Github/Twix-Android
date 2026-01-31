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
import com.twix.domain.model.enums.GoalIconType
import com.twix.ui.extension.noRippleClickable

@Composable
fun EmojiPicker(
    icon: GoalIconType,
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
            painter = painterResource(icon.toRes()),
            contentDescription = "emoji",
            modifier =
                Modifier
                    .size(56.dp),
        )
    }
}

@Composable
fun GoalIconType.toRes(): Int =
    when (this) {
        GoalIconType.DEFAULT -> R.drawable.ic_default
        GoalIconType.CLEAN -> R.drawable.ic_clean
        GoalIconType.EXERCISE -> R.drawable.ic_exercise
        GoalIconType.BOOK -> R.drawable.ic_book
        GoalIconType.PENCIL -> R.drawable.ic_pencil
        GoalIconType.HEALTH -> R.drawable.ic_health
        GoalIconType.HEART -> R.drawable.ic_heart
        GoalIconType.LAPTOP -> R.drawable.ic_laptop
    }
