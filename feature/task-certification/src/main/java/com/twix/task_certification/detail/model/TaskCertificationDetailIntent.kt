package com.twix.task_certification.detail.model

import com.twix.domain.model.enums.GoalReactionType
import com.twix.ui.base.Intent

sealed interface TaskCertificationDetailIntent : Intent {
    data class InitGoal(
        val goalId: Long,
    ) : TaskCertificationDetailIntent

    data class Reaction(
        val type: GoalReactionType,
    ) : TaskCertificationDetailIntent
}
