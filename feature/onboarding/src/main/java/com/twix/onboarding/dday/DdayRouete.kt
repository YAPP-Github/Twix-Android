package com.twix.onboarding.dday

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DdayRouete(
    onComplete: () -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("디데이 등록 화면")

        Button(
            onClick = { onBack() },
        ) {
            Text("뒤로")
        }

        Button(
            onClick = { onComplete() },
        ) {
            Text("다음")
        }
    }
}
