package com.twix.task_certification.detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.button.AppRoundButton
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.task_certification.R
import com.twix.designsystem.R as DesR

@Composable
fun NoCertificationContent(
    buttonTitle: String,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth()) {
        Column(
            Modifier.align(Alignment.TopCenter),
        ) {
            Spacer(Modifier.height(105.dp))
            AppRoundButton(
                modifier =
                    Modifier
                        .width(150.dp)
                        .height(74.dp),
                text = buttonTitle,
                textColor = GrayColor.C500,
                backgroundColor = CommonColor.White,
            )
        }

        Image(
            imageVector = ImageVector.vectorResource(DesR.drawable.ic_keepi_sting),
            contentDescription = null,
            modifier =
                Modifier
                    .padding(end = 24.dp, top = 15.dp)
                    .align(Alignment.TopEnd),
        )
    }
}

@Preview
@Composable
private fun NoCertificationContentPreview() {
    TwixTheme {
        NoCertificationContent(
            buttonTitle = stringResource(R.string.task_certification_detail_partner_sting),
        )
    }
}
