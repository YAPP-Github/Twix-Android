package com.twix.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.onboarding.couple.CoupleConnectRoute
import com.twix.onboarding.dday.DdayRouete
import com.twix.onboarding.profile.ProfileRoute

object OnboardingNavGraph : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.OnboardingGraph

    override val startDestination: String
        get() = NavRoutes.CoupleConnectionRoute.route

    override val priority: Int = 2

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.CoupleConnectionRoute.route) {
                CoupleConnectRoute(
                    onNext = { navController.navigate(NavRoutes.ProfileRoute.route) },
                )
            }
            composable(NavRoutes.ProfileRoute.route) {
                ProfileRoute(
                    onNext = { navController.navigate(NavRoutes.DdayRoute.route) },
                )
            }
            composable(NavRoutes.DdayRoute.route) {
                DdayRouete(
                    onComplete = {
                        navController.navigate(NavRoutes.MainGraph.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}
