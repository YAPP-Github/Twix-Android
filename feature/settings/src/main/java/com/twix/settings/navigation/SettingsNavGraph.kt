package com.twix.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.navigation.owner.rememberNavGraphOwner
import com.twix.settings.SettingsRoute
import com.twix.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

object SettingsNavGraph : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.SettingsGraph
    override val startDestination: String
        get() = NavRoutes.SettingsRoute.route

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(NavRoutes.SettingsRoute.route) { entry ->
                val graphEntry =
                    rememberNavGraphOwner(
                        navController = navController,
                        graphRoute = NavRoutes.SettingsGraph.route,
                        currentEntry = entry,
                    )
                val viewModel: SettingsViewModel = koinViewModel(viewModelStoreOwner = graphEntry)

                SettingsRoute(
                    viewModel = viewModel,
                    popBackStack = { navController.popBackStack() },
                )
            }
        }
    }
}
