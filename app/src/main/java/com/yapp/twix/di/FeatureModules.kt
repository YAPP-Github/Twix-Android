package com.yapp.twix.di

import com.twix.login.di.loginModule
import org.koin.core.module.Module

val featureModules: List<Module> =
    listOf(
        loginModule,
    )
