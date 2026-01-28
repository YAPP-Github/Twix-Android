package com.twix.home

import androidx.lifecycle.viewModelScope
import com.twix.designsystem.components.toast.ToastManager
import com.twix.domain.model.enums.WeekNavigation
import com.twix.home.model.CalendarState
import com.twix.home.model.HomeUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

class HomeViewModel(
    private val toastManager: ToastManager,
) : BaseViewModel<HomeUiState, HomeIntent, HomeSideEffect>(
        HomeUiState(),
    ) {
    val calendarState: StateFlow<CalendarState> =
        uiState
            .map { state ->
                CalendarState(
                    visibleDate = state.visibleDate,
                    selectedDate = state.selectedDate,
                    monthYearText = state.monthYear,
                )
            }.distinctUntilChanged()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue =
                    CalendarState(
                        visibleDate = currentState.visibleDate,
                        selectedDate = currentState.selectedDate,
                        monthYearText = currentState.monthYear,
                    ),
            )

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

        reduce { copy(selectedDate = date, referenceDate = date) }
    }

    private fun shiftWeek(action: WeekNavigation) {
        val newReference =
            when (action) {
                WeekNavigation.NEXT -> currentState.referenceDate.plusWeeks(1)
                WeekNavigation.PREVIOUS -> currentState.referenceDate.minusWeeks(1)
                WeekNavigation.TODAY -> LocalDate.now()
            }
        if (currentState.referenceDate == newReference) return
        if (action == WeekNavigation.TODAY) updateDate(newReference)
        reduce { copy(referenceDate = newReference) }
    }

    private fun updateVisibleDate(date: LocalDate) {
        if (currentState.visibleDate.month == date.month) return

        reduce { copy(visibleDate = date) }
    }
}
