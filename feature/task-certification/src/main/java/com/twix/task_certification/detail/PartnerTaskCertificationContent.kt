package com.twix.task_certification.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.twix.designsystem.components.comment.CommentTextField
import com.twix.designsystem.components.comment.model.CommentUiModel
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.R
import com.twix.task_certification.certification.component.ReactionBox
import com.twix.task_certification.detail.component.CertificationCard
import com.twix.task_certification.detail.component.NoCertificationContent
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
            PhotoLogCard(uiModel)

            ForegroundCard(uiModel)
        }

        ReactionSection(uiModel.isCertificated, uiModel.reaction, onClickReaction)
    }
}

@Composable
fun PhotoLogCard(uiModel: PhotologDetailUiModel) {
    Column {
        CertificationCard(
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
    CertificationCard(
        rotation = -8f,
        background = CommonColor.White,
        borderColor = GrayColor.C500,
    ) {
        if (uiModel.isCertificated) {
            CertificatedCardContent(uiModel)
        } else {
            NotCertificatedCardContent(uiModel.nickName)
        }
    }
}

@Composable
private fun CertificatedCardContent(uiModel: PhotologDetailUiModel) {
    Box(Modifier.fillMaxSize()) {
        AsyncImage(
            model =
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(uiModel.imageUrl)
                    .crossfade(true)
                    .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        CommentTextField(
            uiModel = CommentUiModel(TextFieldValue(uiModel.comment ?: "")),
            onCommentChanged = {},
            onFocusChanged = {},
            onPositioned = {},
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 28.dp),
        )
    }
}

@Composable
private fun NotCertificatedCardContent(nickname: String) {
    AppText(
        text =
            stringResource(
                R.string.task_certification_detail_partner_not_task_certification,
            ).format(nickname),
        style = AppTextStyle.H2,
        color = GrayColor.C500,
    )
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
