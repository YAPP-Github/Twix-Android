package com.twix.goal_manage

import com.twix.designsystem.R
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.enums.WeekNavigation
import com.twix.domain.repository.GoalRepository
import com.twix.goal_manage.model.GoalManageUiState
import com.twix.ui.base.BaseViewModel
import java.time.LocalDate

class GoalManageViewModel(
    private val goalRepository: GoalRepository,
) : BaseViewModel<GoalManageUiState, GoalManageIntent, GoalManageSideEffect>(GoalManageUiState()) {
    override suspend fun handleIntent(intent: GoalManageIntent) {
        when (intent) {
            is GoalManageIntent.EndGoal -> endGoal(intent.id)
            is GoalManageIntent.DeleteGoal -> deleteGoal(intent.id)
            is GoalManageIntent.SetSelectedDate -> setSelectedDate(intent.date)
            GoalManageIntent.NextWeek -> shiftWeek(WeekNavigation.NEXT)
            GoalManageIntent.PreviousWeek -> shiftWeek(WeekNavigation.PREVIOUS)
        }
    }

    private fun endGoal(id: Long) {
        // TODO: 서버 통신 로직 추가
    }

    private fun deleteGoal(id: Long) {
        val previousSummaries = currentState.goalSummaries
        val newSummaries = currentState.goalSummaries.filter { it.goalId != id }
        reduce { copy(goalSummaries = newSummaries) }

        launchResult(
            block = { goalRepository.deleteGoal(id) },
            onSuccess = {},
            onError = {
                reduce { copy(goalSummaries = previousSummaries) }
                emitSideEffect(GoalManageSideEffect.ShowToast(R.string.toast_delete_goal_failed, ToastType.ERROR))
            },
        )
    }

    private fun setSelectedDate(date: LocalDate) {
        reduce { copy(selectedDate = date) }
    }

    private fun shiftWeek(action: WeekNavigation) {
        val newReference =
            when (action) {
                WeekNavigation.NEXT -> currentState.referenceDate.plusWeeks(1)
                WeekNavigation.PREVIOUS -> currentState.referenceDate.minusWeeks(1)
                WeekNavigation.TODAY -> LocalDate.now()
            }
        if (currentState.referenceDate == newReference) return
        reduce { copy(referenceDate = newReference) }
    }
}
