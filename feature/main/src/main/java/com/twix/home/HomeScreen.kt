package com.twix.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.R
import com.twix.designsystem.theme.GrayColor
import com.twix.home.component.EmptyGoalGuide
import com.twix.home.component.HomeTopBar
import com.twix.home.component.WeeklyCalendar
import com.twix.home.model.HomeUiState
import com.twix.ui.extension.noRippleClickable
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
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
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

            Spacer(Modifier.height(12.dp))

            EmptyGoalGuide(modifier = Modifier.weight(1f))
        }

        AddGoalButton(
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 12.dp, end = 16.dp),
            onClick = {},
        )
    }
}

@Composable
private fun AddGoalButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(56.dp)
                .background(GrayColor.C500, CircleShape)
                .noRippleClickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = "add goal",
            modifier =
                Modifier
                    .size(40.dp),
        )
    }
}
