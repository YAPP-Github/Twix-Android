package com.twix.home

import com.twix.ui.base.Intent
import java.time.LocalDate

sealed interface HomeIntent : Intent {
    data class SelectDate(
        val date: LocalDate,
    ) : HomeIntent

    data object PreviousWeek : HomeIntent

    data object NextWeek : HomeIntent

    data object MoveToToday : HomeIntent

    data class UpdateVisibleDate(
        val date: LocalDate,
    ) : HomeIntent
}
