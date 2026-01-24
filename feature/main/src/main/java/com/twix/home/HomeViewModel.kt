package com.twix.home

import com.twix.home.model.HomeUiState
import com.twix.ui.base.BaseViewModel
import java.time.LocalDate

class HomeViewModel :
    BaseViewModel<HomeUiState, HomeIntent, HomeSideEffect>(
        HomeUiState(),
    ) {
    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectDate -> updateDate(intent.date)
            HomeIntent.NextWeek -> shiftWeek(1)
            HomeIntent.PreviousWeek -> shiftWeek(-1)
            is HomeIntent.UpdateVisibleDate -> updateVisibleDate(intent.date)
        }
    }

    private fun updateDate(date: LocalDate) {
        if (currentState.selectedDate == date) return

        if (date.month != currentState.visibleDate.month) updateVisibleDate(date)

        reduce { copy(selectedDate = date) }
    }

    private fun shiftWeek(offset: Long) {
        val newReference = currentState.referenceDate.plusDays(offset * 7)
        if (currentState.referenceDate == newReference) return
        reduce { copy(referenceDate = newReference) }
    }

    private fun updateVisibleDate(date: LocalDate) {
        if (currentState.visibleDate.month == date.month) return

        reduce { copy(visibleDate = date) }
    }
}
