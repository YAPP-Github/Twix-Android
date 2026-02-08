package com.twix.task_certification.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState

class TaskCertificationDetailPreviewProvider : PreviewParameterProvider<TaskCertificationDetailUiState> {
    override val values =
        sequenceOf(
            TaskCertificationPreviewData.myState(),
            TaskCertificationPreviewData.partnerState(),
        )
}
