package com.twix.goal_editor

import com.twix.domain.model.enums.RepeatType
import com.twix.ui.base.Intent
import java.time.LocalDate

sealed interface GoalEditorIntent : Intent {
    data class SelectIcon(
        val iconId: Long,
    ) : GoalEditorIntent

    data class UpdateTitle(
        val title: String,
    ) : GoalEditorIntent

    data class UpdateRepeatType(
        val repeatType: RepeatType,
    ) : GoalEditorIntent

    data class UpdateRepeatCount(
        val repeatCount: Int,
    ) : GoalEditorIntent

    data class UpdateStartDate(
        val startDate: LocalDate,
    ) : GoalEditorIntent

    data class UpdateEndDate(
        val endDate: LocalDate,
    ) : GoalEditorIntent

    data object Save : GoalEditorIntent
}
