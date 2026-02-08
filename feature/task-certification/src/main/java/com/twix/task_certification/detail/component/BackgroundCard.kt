package com.twix.task_certification.detail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.PhotologDetailUiModel

@Composable
fun BackgroundCard(
    uiModel: PhotologDetailUiModel,
    buttonTitle: String,
) {
    Column {
        PhotologCard(
            background = GrayColor.C200,
            borderColor = GrayColor.C500,
        )
        if (uiModel.isCertificated) {
            AppText(
                text = uiModel.uploadedAt,
                style = AppTextStyle.B4,
                color = GrayColor.C500,
                modifier =
                    Modifier
                        .padding(end = 36.dp, top = 14.dp)
                        .align(Alignment.End),
            )
        } else {
            NoCertificationContent(buttonTitle)
        }
    }
}

@Preview
@Composable
fun PreviewBackgroundCard() {
    TwixTheme {
        BackgroundCard(
            buttonTitle = stringResource(R.string.task_certification_detail_partner_sting),
            uiModel =
                PhotologDetailUiModel(
                    uploadedAt = "2023.10.31 23:59",
                ),
        )
    }
}
