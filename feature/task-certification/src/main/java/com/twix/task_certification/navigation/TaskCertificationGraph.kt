package com.twix.task_certification.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.task_certification.certification.TaskCertificationRoute
import com.twix.task_certification.detail.TaskCertificationDetailRoute

object TaskCertificationGraph : NavGraphContributor {
    override val graphRoute: NavRoutes
        get() = NavRoutes.TaskCertificationGraph
    override val startDestination: String
        get() = NavRoutes.TaskCertificationDetailRoute.route

    override fun NavGraphBuilder.registerGraph(navController: NavHostController) {
        navigation(
            route = graphRoute.route,
            startDestination = startDestination,
        ) {
            composable(
                route = NavRoutes.TaskCertificationDetailRoute.route,
                arguments =
                    listOf(
                        navArgument(NavRoutes.TaskCertificationDetailRoute.ARG_GOAL_ID) {
                            defaultValue = -1L
                        },
                        navArgument(NavRoutes.TaskCertificationDetailRoute.ARG_GOAL_TITLE) {
                            defaultValue = ""
                        },
                    ),
            ) { backStackEntry ->
                val id =
                    backStackEntry.arguments?.getLong(NavRoutes.TaskCertificationDetailRoute.ARG_GOAL_ID)
                        ?: -1

                val title =
                    backStackEntry.arguments?.getString(NavRoutes.TaskCertificationDetailRoute.ARG_GOAL_TITLE)
                        ?: ""

                TaskCertificationDetailRoute(
                    goalId = id,
                    goalTitle = title,
                )
            }

            composable(NavRoutes.TaskCertificationRoute.route) {
                TaskCertificationRoute(
                    navigateToBack = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}
