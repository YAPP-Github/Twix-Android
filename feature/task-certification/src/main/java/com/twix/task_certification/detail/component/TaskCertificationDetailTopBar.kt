package com.twix.task_certification.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.ui.extension.noRippleClickable
import com.twix.designsystem.R as DesR

@Composable
fun TaskCertificationDetailTopBar(
    goalTitle: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    actionTitle: String?,
    onClickModify: (() -> Unit)?,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
    ) {
        HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .width(60.dp)
                        .fillMaxHeight()
                        .noRippleClickable(onClick = onBack),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(DesR.drawable.ic_arrow3_left),
                    contentDescription = "back",
                    modifier = Modifier.size(24.dp),
                )
            }

            VerticalDivider(thickness = 1.dp, color = GrayColor.C500)

            AppText(
                text = goalTitle,
                style = AppTextStyle.H4Brand,
                color = GrayColor.C500,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )

            VerticalDivider(thickness = 1.dp, color = GrayColor.C500)

            Box(
                modifier =
                    Modifier
                        .width(60.dp)
                        .fillMaxHeight()
                        .background(
                            if (actionTitle == null) {
                                CommonColor.White
                            } else {
                                GrayColor.C100
                            },
                        ).noRippleClickable { onClickModify?.invoke() },
                contentAlignment = Alignment.Center,
            ) {
                actionTitle?.let {
                    AppText(
                        text = it,
                        style = AppTextStyle.T2,
                        color = GrayColor.C500,
                    )
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCertificationDetailTopBarPreview() {
    TwixTheme {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            TaskCertificationDetailTopBar(
                actionTitle = "수정",
                goalTitle = "목표 타이틀",
                onBack = {},
                onClickModify = {},
            )

            TaskCertificationDetailTopBar(
                goalTitle = "목표 타이틀",
                onBack = {},
                actionTitle = null,
                onClickModify = null,
            )
        }
    }
}
