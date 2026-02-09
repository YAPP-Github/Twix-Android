package com.twix.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twix.designsystem.R
import com.twix.designsystem.components.goal.GoalVerificationCell
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.goal.GoalVerification

@Composable
fun GoalVerifications(
    modifier: Modifier = Modifier,
    myVerification: GoalVerification?,
    partnerVerification: GoalVerification?,
    onMyClick: (() -> Unit)? = null,
    onPartnerClick: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .border(1.dp, GrayColor.C500, shape),
    ) {
        GoalVerificationCell(
            modifier = Modifier.weight(1f),
            verification = myVerification,
            emptyContent = {
                EmptyContent(
                    onClick = {},
                )
            },
            onClick = onMyClick,
        )

        VerticalDivider(thickness = 1.dp, color = GrayColor.C500)

        GoalVerificationCell(
            modifier = Modifier.weight(1f),
            verification = partnerVerification,
            emptyContent = {
                EmptyContent(
                    isPartner = true,
                    onClick = {},
                )
            },
            onClick = onPartnerClick,
        )
    }
}

@Composable
private fun EmptyContent(
    isPartner: Boolean = false,
    onClick: () -> Unit,
) {
    if (isPartner) {
        Column(
            verticalArrangement = Arrangement.spacedBy(9.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_goal_action_poke),
                contentDescription = null,
                modifier = Modifier.size(width = 78.dp, height = 52.dp),
            )
            // TODO: 찌르기 API 구현된 이후에 찌르기 버튼 추가
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_goal_action_cheer),
                contentDescription = null,
                modifier = Modifier.size(width = 56.dp, height = 64.dp),
            )

            AppText(
                text = stringResource(R.string.goal_action_cheer),
                style = AppTextStyle.B4,
                color = GrayColor.C400,
            )
        }
    }
}
