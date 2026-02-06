package com.yapp.twix.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.twix.designsystem.components.toast.ToastHost
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.theme.TwixTheme
import com.twix.navigation.AppNavHost
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val toastManager by inject<ToastManager>()

            TwixTheme {
                Box(
                    modifier =
                        Modifier
                            // .safeContentPadding()
                            .fillMaxSize(),
                ) {
                    AppNavHost()

                    ToastHost(
                        toastManager = toastManager,
                        modifier =
                            Modifier
                                .align(Alignment.BottomCenter)
                                .imePadding(),
                    )
                }
            }
        }
    }
}
