package com.yapp.twix.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.twix.designsystem.components.toast.ToastHost
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.theme.TwixTheme
import com.twix.navigation.AppNavHost
import org.koin.android.ext.android.inject
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val toastManager by inject<ToastManager>()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

            TwixTheme {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .safeContentPadding(),
                ) {
                    AppNavHost()

                    ToastHost(
                        toastManager = toastManager,
                        modifier =
                            Modifier
                                .align(Alignment.BottomCenter),
                    )
                }
            }
        }
    }
}
