package com.twix.task_certification.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.twix.designsystem.theme.TwixTheme
import com.twix.task_certification.R
import com.twix.task_certification.detail.component.BackgroundCard
import com.twix.task_certification.detail.component.ForegroundCard
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import com.twix.task_certification.detail.preview.PhotologPreviewProvider
import com.twix.designsystem.R as DesR

@Composable
fun MyTaskCertificationContent(
    uiModel: PhotologDetailUiModel,
    onClickUpload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxWidth()) {
        BackgroundCard(
            uiModel = uiModel,
            buttonTitle = stringResource(R.string.task_certification_image_upload),
            rotation = -8f,
            onClick = onClickUpload,
        )

        ForegroundCard(
            uiModel,
            stringResource(DesR.string.keep_it_up)
                .format(uiModel.nickName),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyTaskCertificationPreview(
    @PreviewParameter(PhotologPreviewProvider::class)
    uiModel: PhotologDetailUiModel,
) {
    TwixTheme {
        MyTaskCertificationContent(uiModel, onClickUpload = {})
    }
}
