package com.twix.task_certification.detail.preview

import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.detail.model.PhotoLogsUiModel
import com.twix.task_certification.detail.model.PhotologDetailUiModel
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState

object TaskCertificationPreviewData {
    fun myCertificated() =
        PhotologDetailUiModel(
            nickName = "나",
            goalId = 1,
            photologId = 1,
            imageUrl = "https://picsum.photos/400/300",
            comment = "아이수쿠림",
            verificationDate = "",
            uploaderName = "나",
            uploadedAt = "1시간 전",
        )

    fun partnerCertificated() =
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
        )

    fun photoLogs() =
        PhotoLogsUiModel(
            goalId = 1,
            myPhotologs = myCertificated(),
            partnerPhotologs = partnerCertificated(),
        )

    fun myState() =
        TaskCertificationDetailUiState(
            goalTitle = "아이스크림 먹기",
            currentShow = BetweenUs.ME,
            photoLogs = photoLogs(),
        )

    fun partnerState() =
        TaskCertificationDetailUiState(
            goalTitle = "아이스크림 먹기",
            currentShow = BetweenUs.PARTNER,
            photoLogs = photoLogs(),
        )
}
