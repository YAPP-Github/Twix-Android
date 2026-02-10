package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.photolog.PhotoLogs

@Immutable
data class PhotoLogsUiModel(
    val goalId: Long = -1,
    val myPhotologs: PhotologDetailUiModel = PhotologDetailUiModel(),
    val partnerPhotologs: PhotologDetailUiModel = PhotologDetailUiModel(),
) {
    fun updatePartnerReaction(type: GoalReactionType) =
        copy(
            partnerPhotologs = partnerPhotologs.copy(reaction = type),
        )
}

fun PhotoLogs.toUiModel(): PhotoLogsUiModel {
    if (photologDetails.size != 2) return PhotoLogsUiModel()

    val my = photologDetails.first { it.isMine }
    val partner = photologDetails.first { !it.isMine }

    return PhotoLogsUiModel(
        goalId = goalId,
        myPhotologs = my.toUiModel(myNickname),
        partnerPhotologs = partner.toUiModel(partnerNickname),
    )
}
