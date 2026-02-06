package com.twix.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.onboarding.couple.CoupleConnectRoute
import com.twix.onboarding.dday.DdayRoute
import com.twix.onboarding.invite.InviteCodeRoute
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
                    navigateToNext = {
                        navController.navigate(NavRoutes.InviteRoute.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(NavRoutes.InviteRoute.route) {
                InviteCodeRoute(
                    navigateToNext = {
                        navController.navigate(NavRoutes.ProfileRoute.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(NavRoutes.ProfileRoute.route) {
                ProfileRoute(
                    navigateToDday = {
                        navController.navigate(NavRoutes.DdayRoute.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                        }
                    },
                    navigateToHome = {
                        navController.navigate(NavRoutes.MainGraph.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(NavRoutes.DdayRoute.route) {
                DdayRoute(
                    navigateToHome = {
                        navController.navigate(NavRoutes.MainGraph.route) {
                            popUpTo(graphRoute.route) { inclusive = true }
                        }
                    },
                    navigateToBack = {
                        navController.navigate(NavRoutes.ProfileRoute.route) {
                            popUpTo(graphRoute.route) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
    }
}
