package com.yapp.twix.di

import com.peto.onboarding.di.onBoardingModule
import com.twix.home.di.homeModule
import com.twix.login.di.loginModule
import com.twix.main.di.mainModule
import org.koin.core.module.Module

val featureModules: List<Module> =
    listOf(
        loginModule,
        mainModule,
        homeModule,
        onBoardingModule,
    )
