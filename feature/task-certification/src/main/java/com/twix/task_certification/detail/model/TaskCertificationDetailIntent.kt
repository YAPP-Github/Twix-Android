package com.twix.task_certification.detail.model

import com.twix.ui.base.Intent

sealed interface TaskCertificationDetailIntent : Intent {
    data class InitGoal(
        val goalId: Long,
        val goalTitle: String,
    ) : TaskCertificationDetailIntent
}
