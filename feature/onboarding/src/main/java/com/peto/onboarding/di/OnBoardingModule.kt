package com.peto.onboarding.di

import com.peto.onboarding.navigation.OnboardingNavGraph
import com.peto.onboarding.vm.OnBoardingViewModel
import com.twix.navigation.NavRoutes
import com.twix.navigation.base.NavGraphContributor
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val onBoardingModule =
    module {
        viewModelOf(::OnBoardingViewModel)
        single<NavGraphContributor>(named(NavRoutes.OnboardingRoute.route)) { OnboardingNavGraph }
    }
