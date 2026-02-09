package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.enums.GoalReactionType
import com.twix.ui.base.State

@Immutable
data class TaskCertificationDetailUiState(
    val goalTitle: String = "",
    val currentShow: BetweenUs = BetweenUs.PARTNER,
    val photoLogs: PhotoLogsUiModel = PhotoLogsUiModel(),
) : State {
    val canModify: Boolean
        get() = currentShow == BetweenUs.ME && photoLogs.myPhotologs.isCertificated

    val canReaction: Boolean
        get() =
            currentShow == BetweenUs.PARTNER &&
                photoLogs.partnerPhotologs.isCertificated

    fun toggleBetweenUs() =
        copy(
            currentShow =
                when (currentShow) {
                    BetweenUs.ME -> BetweenUs.PARTNER
                    BetweenUs.PARTNER -> BetweenUs.ME
                },
        )

    fun updatePartnerReaction(type: GoalReactionType) = copy(photoLogs = photoLogs.updatePartnerReaction(type))
}
