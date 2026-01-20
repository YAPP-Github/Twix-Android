package com.twix.home

import com.twix.ui.base.Intent
import java.time.LocalDate

sealed interface HomeIntent : Intent {
    data class SelectDate(
        val date: LocalDate,
    ) : HomeIntent
}
