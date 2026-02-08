package com.twix.task_certification.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.R
import com.twix.task_certification.certification.component.ReactionBox
import com.twix.task_certification.detail.component.CertificatedCard
import com.twix.task_certification.detail.component.NoCertificationContent
import com.twix.task_certification.detail.component.PhotologCard
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import java.time.LocalDate

@Composable
fun PartnerTaskCertificationContent(
    uiModel: PhotologDetailUiModel,
    onClickReaction: (GoalReactionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(Modifier.fillMaxWidth()) {
            BackgroundCard(uiModel)

            ForegroundCard(uiModel)
        }

        ReactionSection(uiModel.isCertificated, uiModel.reaction, onClickReaction)
    }
}

@Composable
fun BackgroundCard(uiModel: PhotologDetailUiModel) {
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
            NoCertificationContent(
                buttonTitle = stringResource(R.string.task_certification_detail_partner_sting),
            )
        }
    }
}

@Composable
private fun ForegroundCard(uiModel: PhotologDetailUiModel) {
    PhotologCard(
        rotation = -8f,
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

@Composable
private fun ReactionSection(
    visible: Boolean,
    reaction: GoalReactionType? = null,
    onClickReaction: (GoalReactionType) -> Unit,
) {
    if (visible) {
        Spacer(Modifier.height(85.dp))

        ReactionBox(
            selectedReaction = reaction,
            onSelectReaction = onClickReaction,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
    }
}

@Preview(name = "파트너의 인증이 완료된 상태", showBackground = true)
@Composable
private fun PartnerTaskCertificationContentPreview1() {
    TwixTheme {
        PartnerTaskCertificationContent(
            uiModel =
                PhotologDetailUiModel(
                    nickName = "민정",
                    goalId = 1,
                    photologId = 1,
                    imageUrl = "https://picsum.photos/400/300",
                    comment = "아이수쿠림",
                    verificationDate = LocalDate.now(),
                    uploaderName = "민정",
                    uploadedAt = "6시간 전",
                    reaction = GoalReactionType.FUCK,
                ),
            onClickReaction = {},
        )
    }
}

@Preview(name = "파트너의 인증이 완료되지 않은 상태", showBackground = true)
@Composable
private fun PartnerTaskCertificationContentPreview2() {
    TwixTheme {
        PartnerTaskCertificationContent(
            uiModel =
                PhotologDetailUiModel(
                    nickName = "민정",
                ),
            onClickReaction = {},
        )
    }
}
