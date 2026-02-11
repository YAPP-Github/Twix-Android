package com.twix.task_certification.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.detail.model.PhotoLogsUiModel
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState

class TaskCertificationDetailPreviewProvider : PreviewParameterProvider<TaskCertificationDetailUiState> {
    override val values =
        sequenceOf(
            TaskCertificationDetailUiState(
                currentShow = BetweenUs.ME,
                photoLogs =
                    PhotoLogsUiModel(
                        goalId = 1,
                        goalTitle = "아이수크림먹기",
                        myPhotologs =
                            PhotologDetailUiModel(
                                nickName = "나",
                                goalId = 1,
                            ),
                        partnerPhotologs =
                            PhotologDetailUiModel(
                                nickName = "민정",
                                goalId = 1,
                                photologId = 2,
                                imageUrl = "https://picsum.photos/400/301",
                                comment = "인증합니다",
                                verificationDate = "",
                                uploaderName = "민정",
                                uploadedAt = "2시간 전",
                                reaction = GoalReactionType.FUCK,
                            ),
                    ),
            ),
            TaskCertificationDetailUiState(
                currentShow = BetweenUs.PARTNER,
                photoLogs =
                    PhotoLogsUiModel(
                        goalId = 1,
                        goalTitle = "아이수크림먹기",
                        myPhotologs =
                            PhotologDetailUiModel(
                                nickName = "나",
                                goalId = 1,
                                photologId = 1,
                                imageUrl = "https://picsum.photos/400/300",
                                comment = "아이수쿠림",
                                verificationDate = "",
                                uploaderName = "나",
                                uploadedAt = "1시간 전",
                            ),
                        partnerPhotologs =
                            PhotologDetailUiModel(
                                nickName = "민정",
                                goalId = 1,
                            ),
                    ),
            ),
        )
}
