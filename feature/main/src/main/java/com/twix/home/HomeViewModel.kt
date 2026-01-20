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
        }
    }

    private fun updateDate(date: LocalDate) {
        if (currentState.selectedDate == date) return

        reduce { copy(selectedDate = date) }
    }
}
