package com.twix.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twix.login.LoginRoute
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor

object LoginNavGraph : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.LoginGraph
    override val startDestination: String
        get() = NavRoutes.LoginRoute.route

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.LoginRoute.route) {
                LoginRoute(
                    navigateToHome = {
                        navController.navigate(NavRoutes.MainGraph.route) {
                            popUpTo(NavRoutes.LoginGraph.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToOnBoarding = { },
                )
            }
        }
    }
}
