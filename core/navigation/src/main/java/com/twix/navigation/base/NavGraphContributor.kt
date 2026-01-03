package com.twix.navigation.base

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.twix.navigation.NavRoutes

/**
 * priority는 우선적으로 등록해야 하는 Graph에서 값을 낮춰 사용하면 됩니다.
 * ex) SplashGraph는 앱 내에서 가장 먼저 실행되어야 하는 그래프이므로 priority를 낮춰주면 됩니다.
 **/
interface NavGraphContributor {
    val graphRoute: NavRoutes
    val startDestination: String
    val priority: Int get() = 100

    fun NavGraphBuilder.registerGraph(navController: NavHostController)
}