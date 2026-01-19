package com.twix.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.twix.navigation.base.NavGraphContributor
import org.koin.compose.getKoin

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val koin = getKoin()
    val contributors =
        remember {
            koin.getAll<NavGraphContributor>().sortedBy { it.priority }
        }
    val start =
        contributors
            .firstOrNull { it.graphRoute == NavRoutes.LoginGraph }
            ?.graphRoute
            ?: error("해당 Graph를 찾을 수 없습니다.")
    val duration = 300

    NavHost(
        navController = navController,
        startDestination = start.route,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(duration, easing = FastOutSlowInEasing),
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(duration, easing = FastOutSlowInEasing),
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(duration, easing = FastOutSlowInEasing),
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(duration, easing = FastOutSlowInEasing),
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) {
        contributors.forEach { with(it) { registerGraph(navController) } }
    }
}
