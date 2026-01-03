package com.twix.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twix.login.LoginScreen
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor

object LoginNavGraph: NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.LoginGraph
    override val startDestination: String
        get() = NavRoutes.Login.route

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination
        ) {
            composable(NavRoutes.Login.route) {
                LoginScreen()
            }
        }
    }
}