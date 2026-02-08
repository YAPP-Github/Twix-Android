package com.twix.task_certification.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.R
import com.twix.task_certification.certification.component.ReactionBox
import com.twix.task_certification.detail.component.BackgroundCard
import com.twix.task_certification.detail.component.ForegroundCard
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import com.twix.task_certification.detail.preview.PhotologPreviewProvider

@Composable
fun PartnerTaskCertificationContent(
    uiModel: PhotologDetailUiModel,
    onClickReaction: (GoalReactionType) -> Unit,
    onClickSting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Box(Modifier.fillMaxWidth()) {
            BackgroundCard(
                uiModel = uiModel,
                buttonTitle = stringResource(R.string.task_certification_detail_partner_sting),
                onClick = onClickSting,
            )

            ForegroundCard(
                uiModel = uiModel,
                noCertificatedText =
                    stringResource(R.string.task_certification_detail_partner_not_task_certification)
                        .format(uiModel.nickName),
                rotation = -8f,
            )
        }

        ReactionSection(uiModel.isCertificated, uiModel.reaction, onClickReaction)
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

@Preview(showBackground = true)
@Composable
private fun PartnerTaskCertificationPreview(
    @PreviewParameter(PhotologPreviewProvider::class)
    uiModel: PhotologDetailUiModel,
) {
    TwixTheme {
        PartnerTaskCertificationContent(
            uiModel = uiModel,
            onClickReaction = {},
            onClickSting = {},
        )
    }
}
