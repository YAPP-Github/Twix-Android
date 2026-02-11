package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.enums.GoalReactionType
import com.twix.ui.base.State

@Immutable
data class TaskCertificationDetailUiState(
    val currentGoalId: Long = -1L,
    val currentShow: BetweenUs = BetweenUs.PARTNER,
    val photoLogs: PhotoLogsUiModel = PhotoLogsUiModel(),
) : State {
    val currentGoal: GoalPhotologUiModel
        get() = photoLogs[currentGoalId]

    val isDisplayedGoalCertificated: Boolean
        get() =
            when (currentShow) {
                BetweenUs.ME -> currentGoal.isCertificated
                BetweenUs.PARTNER -> currentGoal.isPartnerCertificated
            }

    val displayedGoalUpdateAt: String
        get() =
            when (currentShow) {
                BetweenUs.ME -> currentGoal.myUpdatedDate
                BetweenUs.PARTNER -> currentGoal.partnerUpdatedDate
            }

    val displayedGoalImageUrl: String?
        get() =
            when (currentShow) {
                BetweenUs.ME -> currentGoal.myPhotolog?.imageUrl
                BetweenUs.PARTNER -> currentGoal.partnerPhotolog?.imageUrl
            }

    val displayedGoalComment: String?
        get() =
            when (currentShow) {
                BetweenUs.ME -> currentGoal.myPhotolog?.comment
                BetweenUs.PARTNER -> currentGoal.partnerPhotolog?.comment
            }

    val canModify: Boolean
        get() =
            currentShow == BetweenUs.ME && currentGoal.isCertificated

    val canReaction: Boolean
        get() =
            currentShow == BetweenUs.PARTNER && currentGoal.isPartnerCertificated

    fun toggleBetweenUs() =
        copy(
            currentShow =
                when (currentShow) {
                    BetweenUs.ME -> BetweenUs.PARTNER
                    BetweenUs.PARTNER -> BetweenUs.ME
                },
        )

    fun updatePartnerReaction(type: GoalReactionType) = copy(photoLogs = photoLogs.updatePartnerReaction(currentGoalId, type))
}
