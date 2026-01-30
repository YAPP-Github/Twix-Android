package com.yapp.twix.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Modifier
import com.twix.designsystem.theme.TwixTheme
import com.twix.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TwixTheme {
                Box(
                    modifier =
                        Modifier
                            .safeContentPadding()
                            .fillMaxSize(),
                ) {
                    AppNavHost()
                }
            }
        }
    }
}
