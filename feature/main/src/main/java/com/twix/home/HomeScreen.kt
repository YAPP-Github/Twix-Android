package com.twix.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.home.component.HomeTopBar
import com.twix.home.model.HomeUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
    )
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
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

        Spacer(Modifier.weight(1f))
    }
}
