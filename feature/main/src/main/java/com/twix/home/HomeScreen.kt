package com.twix.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.home.component.HomeTopBar
import com.twix.home.component.WeeklyCalendar
import com.twix.home.model.HomeUiState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSelectDate = { viewModel.dispatch(HomeIntent.SelectDate(it)) },
        onPreviousWeek = { viewModel.dispatch(HomeIntent.PreviousWeek) },
        onNextWeek = { viewModel.dispatch(HomeIntent.NextWeek) },
        onUpdateVisibleDate = { viewModel.dispatch(HomeIntent.UpdateVisibleDate(it)) },
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onSelectDate: (LocalDate) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onUpdateVisibleDate: (LocalDate) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        HomeTopBar(
            monthYearText = uiState.monthYear,
            onNotificationClick = {},
            onSettingClick = {},
        )

        WeeklyCalendar(
            selectedDate = uiState.selectedDate,
            referenceDate = uiState.referenceDate,
            onSelectDate = onSelectDate,
            onPreviousWeek = onPreviousWeek,
            onNextWeek = onNextWeek,
            onUpdateVisibleDate = onUpdateVisibleDate,
        )

        Spacer(Modifier.weight(1f))
    }
}
