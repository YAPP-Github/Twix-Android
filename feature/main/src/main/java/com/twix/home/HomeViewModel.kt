package com.twix.home

import com.twix.designsystem.components.toast.ToastManager
import com.twix.domain.model.enums.WeekNavigation
import com.twix.home.model.HomeUiState
import com.twix.ui.base.BaseViewModel
import java.time.LocalDate

class HomeViewModel(
    private val toastManager: ToastManager,
) : BaseViewModel<HomeUiState, HomeIntent, HomeSideEffect>(
        HomeUiState(),
    ) {
    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectDate -> updateDate(intent.date)
            HomeIntent.NextWeek -> shiftWeek(WeekNavigation.NEXT)
            HomeIntent.PreviousWeek -> shiftWeek(WeekNavigation.PREVIOUS)
            HomeIntent.MoveToToday -> shiftWeek(WeekNavigation.TODAY)
            is HomeIntent.UpdateVisibleDate -> updateVisibleDate(intent.date)
        }
    }

    private fun updateDate(date: LocalDate) {
        if (currentState.selectedDate == date) return

        if (date.month != currentState.visibleDate.month) updateVisibleDate(date)

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

    private fun updateVisibleDate(date: LocalDate) {
        if (currentState.visibleDate.month == date.month) return

        reduce { copy(visibleDate = date) }
    }
}
