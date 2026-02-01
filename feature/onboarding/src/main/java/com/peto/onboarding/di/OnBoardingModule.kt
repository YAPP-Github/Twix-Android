package com.peto.onboarding.di

import com.peto.onboarding.vm.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onBoardingModule =
    module {
        viewModelOf(::OnBoardingViewModel)
    }
