package com.twix.goal_editor

import androidx.lifecycle.viewModelScope
import com.twix.designsystem.R
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.RepeatCycle
import com.twix.domain.model.goal.CreateGoalParam
import com.twix.domain.repository.GoalRepository
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class GoalEditorViewModel(
    private val goalRepository: GoalRepository,
) : BaseViewModel<GoalEditorUiState, GoalEditorIntent, GoalEditorSideEffect>(
        GoalEditorUiState(),
    ) {
    override suspend fun handleIntent(intent: GoalEditorIntent) {
        when (intent) {
            GoalEditorIntent.Save -> save()
            is GoalEditorIntent.SetIcon -> setIcon(intent.icon)
            is GoalEditorIntent.SetEndDate -> setEndDate(intent.endDate)
            is GoalEditorIntent.SetRepeatCount -> setRepeatCount(intent.repeatCount)
            is GoalEditorIntent.SetRepeatType -> setRepeatType(intent.repeatCycle)
            is GoalEditorIntent.SetStartDate -> setStartDate(intent.startDate)
            is GoalEditorIntent.SetTitle -> setTitle(intent.title)
            is GoalEditorIntent.SetEndDateEnabled -> setEndDateEnabled(intent.enabled)
        }
    }

    private fun setIcon(icon: GoalIconType) {
        reduce { copy(selectedIcon = icon) }
    }

    private fun setTitle(title: String) {
        if (title.isBlank()) return

        reduce { copy(goalTitle = title) }
    }

    private fun setRepeatType(repeatCycle: RepeatCycle) {
        reduce { copy(selectedRepeatCycle = repeatCycle) }
    }

    private fun setRepeatCount(repeatCount: Int) {
        if (repeatCount <= 0) return

        reduce { copy(repeatCount = repeatCount) }
    }

    private fun setStartDate(startDate: LocalDate) {
        reduce { copy(startDate = startDate) }
    }

    private fun setEndDate(endDate: LocalDate) {
        reduce { copy(endDate = endDate) }
    }

    private fun setEndDateEnabled(enabled: Boolean) {
        reduce { copy(endDateEnabled = enabled) }
    }

    private fun save() {
        if (!currentState.isEnabled) return

        if (currentState.endDateEnabled && currentState.endDate.isBefore(currentState.startDate)) {
            viewModelScope.launch {
                emitSideEffect(GoalEditorSideEffect.ShowToast(R.string.toast_end_date_before_start_date, ToastType.ERROR))
            }
            return
        }

        launchResult(
            block = { goalRepository.createGoal(currentState.toCreateParam()) },
            onSuccess = { tryEmitSideEffect(GoalEditorSideEffect.NavigateToHome) },
            onError = { emitSideEffect(GoalEditorSideEffect.ShowToast(R.string.toast_create_goal_failed, ToastType.ERROR)) },
        )
    }

    private fun GoalEditorUiState.toCreateParam(): CreateGoalParam =
        CreateGoalParam(
            name = goalTitle.trim(),
            icon = selectedIcon,
            repeatCycle = selectedRepeatCycle,
            repeatCount = repeatCount,
            startDate = startDate,
            endDate = if (endDateEnabled) endDate else null,
        )
}
