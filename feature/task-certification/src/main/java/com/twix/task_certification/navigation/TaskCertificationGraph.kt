package com.twix.task_certification.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import com.twix.task_certification.certification.TaskCertificationRoute
import com.twix.task_certification.detail.TaskCertificationDetailRoute
import com.twix.task_certification.editor.TaskCertificationEditorRoute

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
                            type = NavType.LongType
                        },
                        navArgument(NavRoutes.TaskCertificationDetailRoute.ARG_DATE) {
                            type = NavType.StringType
                        },
                        navArgument(NavRoutes.TaskCertificationDetailRoute.ARG_BETWEEN_US) {
                            type = NavType.StringType
                        },
                    ),
            ) {
                TaskCertificationDetailRoute(
                    navigateToBack = navController::popBackStack,
                    navigateToUpload = {
                        val destination =
                            NavRoutes.TaskCertificationRoute.createRoute(
                                NavRoutes.TaskCertificationRoute.From.Detail(it),
                            )
                        navController.navigate(destination)
                    },
                    navigateToEditor = { uiState ->
                        val serializer = uiState.toSerializer()
                        navController.navigate(
                            NavRoutes.TaskCertificationEditorRoute.createRoute(
                                serializer,
                            ),
                        )
                    },
                )
            }

            composable(
                route = NavRoutes.TaskCertificationEditorRoute.route,
                arguments =
                    listOf(
                        navArgument(NavRoutes.TaskCertificationEditorRoute.ARG_DATA) {
                            type = NavType.StringType
                        },
                    ),
            ) {
                TaskCertificationEditorRoute(
                    navigateToBack = navController::popBackStack,
                    navigateToCertification = { goalId, photologId, comment ->
                        navController.navigate(
                            NavRoutes.TaskCertificationRoute.createRoute(
                                from =
                                    NavRoutes.TaskCertificationRoute.From.Editor(
                                        goalId,
                                        photologId,
                                        comment,
                                    ),
                            ),
                        )
                    },
                )
            }

            composable(
                route = NavRoutes.TaskCertificationRoute.route,
                arguments =
                    listOf(
                        navArgument(NavRoutes.TaskCertificationRoute.ARG_GOAL_ID) {
                            type = NavType.LongType
                        },
                        navArgument(NavRoutes.TaskCertificationRoute.ARG_FROM) {
                            type = NavType.StringType
                        },
                        navArgument(NavRoutes.TaskCertificationRoute.ARG_PHOTOLOG_ID) {
                            type = NavType.LongType
                        },
                        navArgument(NavRoutes.TaskCertificationRoute.ARG_COMMENT) {
                            type = NavType.StringType
                        },
                    ),
            ) {
                TaskCertificationRoute(
                    navigateToBack = navController::popBackStack,
                    navigateToDetail = {
                        navController.popBackStack(
                            route = NavRoutes.TaskCertificationDetailRoute.route,
                            inclusive = false,
                        )
                    },
                )
            }
        }
    }
}
