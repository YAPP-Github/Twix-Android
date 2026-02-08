package com.twix.task_certification.detail.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.enums.BetweenUs
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import com.twix.designsystem.R as DesR

@Composable
internal fun ForegroundCard(
    uiModel: PhotologDetailUiModel,
    currentShow: BetweenUs,
    rotation: Float,
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
                    when (currentShow) {
                        BetweenUs.ME -> stringResource(DesR.string.keep_it_up)
                        BetweenUs.PARTNER ->
                            stringResource(R.string.task_certification_detail_partner_not_task_certification)
                                .format(uiModel.nickName)
                    },
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
                    verificationDate = "",
                    uploaderName = "참치",
                    uploadedAt = "1시간 전",
                ),
            currentShow = BetweenUs.ME,
            rotation = -8f,
        )
    }
}
