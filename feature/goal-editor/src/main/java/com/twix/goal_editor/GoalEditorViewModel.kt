package com.twix.goal_editor

import com.twix.domain.model.enums.RepeatType
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.base.BaseViewModel
import java.time.LocalDate

class GoalEditorViewModel :
    BaseViewModel<GoalEditorUiState, GoalEditorIntent, GoalEditorSideEffect>(
        GoalEditorUiState(),
    ) {
    override suspend fun handleIntent(intent: GoalEditorIntent) {
        when (intent) {
            GoalEditorIntent.Save -> save()
            is GoalEditorIntent.SelectIcon -> selectIcon(intent.iconId)
            is GoalEditorIntent.UpdateEndDate -> updateEndDate(intent.endDate)
            is GoalEditorIntent.UpdateRepeatCount -> updateRepeatCount(intent.repeatCount)
            is GoalEditorIntent.UpdateRepeatType -> updateRepeatType(intent.repeatType)
            is GoalEditorIntent.UpdateStartDate -> updateStartDate(intent.startDate)
            is GoalEditorIntent.UpdateTitle -> updateTitle(intent.title)
        }
    }

    private fun selectIcon(iconId: Long) {
        if (iconId <= 0) return

        reduce { copy(selectedIconId = iconId) }
    }

    private fun updateTitle(title: String) {
        if (title.isBlank()) return

        reduce { copy(goalTitle = title) }
    }

    private fun updateRepeatType(repeatType: RepeatType) {
        reduce { copy(selectedRepeatType = repeatType) }
    }

    private fun updateRepeatCount(repeatCount: Int) {
        if (repeatCount <= 0) return

        reduce { copy(repeatCount = repeatCount) }
    }

    private fun updateStartDate(startDate: LocalDate) {
        reduce { copy(startDate = startDate) }
    }

    private fun updateEndDate(endDate: LocalDate) {
        reduce { copy(endDate = endDate) }
    }

    private fun save() {
        if (!currentState.isEnabled) return
    }
}
