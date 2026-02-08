package com.twix.task_certification.detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import java.time.LocalDate

@Composable
internal fun ForegroundCard(
    uiModel: PhotologDetailUiModel,
    rotation: Float = 0f,
) {
    PhotologCard(
        rotation = rotation,
        background = CommonColor.White,
        borderColor = GrayColor.C500,
    ) {
        if (uiModel.isCertificated) {
            CertificatedCard(uiModel)
        } else {
            AppText(
                text =
                    stringResource(
                        R.string.task_certification_detail_partner_not_task_certification,
                    ).format(uiModel.nickName),
                style = AppTextStyle.H2,
                color = GrayColor.C500,
            )
        }
    }
}

@Preview
@Composable
private fun ForegroundCardPreview() {
    TwixTheme {
        ForegroundCard(
            uiModel =
                PhotologDetailUiModel(
                    nickName = "페토",
                    goalId = 1,
                    photologId = 1,
                    imageUrl = "",
                    comment = "아이수크림",
                    verificationDate = LocalDate.now(),
                    uploaderName = "참치",
                    uploadedAt = "1시간 전",
                ),
            rotation = -8f,
        )
    }
}

@Preview
@Composable
private fun ForegroundCardUncertificatedPreview() {
    TwixTheme {
        ForegroundCard(
            uiModel =
                PhotologDetailUiModel(
                    nickName = "페토",
                ),
        )
    }
}
