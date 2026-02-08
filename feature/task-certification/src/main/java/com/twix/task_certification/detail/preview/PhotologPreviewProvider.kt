package com.twix.task_certification.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.twix.task_certification.detail.model.PhotologDetailUiModel

class PhotologPreviewProvider : PreviewParameterProvider<PhotologDetailUiModel> {
    override val values =
        sequenceOf(
            TaskCertificationPreviewData.myCertificated(),
            TaskCertificationPreviewData.empty("민정"),
        )
}
