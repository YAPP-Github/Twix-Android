package com.twix.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.theme.CommonColor
import com.twix.home.HomeRoute
import com.twix.main.component.MainBottomBar
import com.twix.main.model.MainTab
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoute(viewModel: MainViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        selectedTab = uiState.selectedTab,
        onTabClick = { tab -> viewModel.dispatch(MainIntent.SelectTab(tab)) },
        content = { tab ->
            when (tab) {
                MainTab.HOME -> HomeRoute()
                MainTab.STATS -> Box(modifier = Modifier.fillMaxSize())
                MainTab.COUPLE -> Box(modifier = Modifier.fillMaxSize())
            }
        },
    )
}

@Composable
private fun MainScreen(
    selectedTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    content: @Composable (MainTab) -> Unit,
) {
    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .background(CommonColor.White),
        bottomBar = {
            MainBottomBar(
                selectedTab = selectedTab,
                onTabClick = onTabClick,
            )
        },
    ) { padding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(CommonColor.White)
                    .padding(padding),
        ) {
            content(selectedTab)
        }
    }
}
