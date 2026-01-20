package com.twix.home.model

import androidx.compose.runtime.Immutable
import com.twix.ui.base.State
import java.time.LocalDate
import java.time.YearMonth

@Immutable
data class HomeUiState(
    val month: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate = LocalDate.now(),
    val dateItems: List<DateItemUiModel> = emptyList(),
) : State {
    val monthYear: String
        get() = "${selectedDate.month.value}월 ${selectedDate.year}"
}
