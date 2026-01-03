package com.twix.login.di

import com.twix.login.navigation.LoginNavGraph
import com.twix.navigation.base.NavGraphContributor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loginModule = module {
    single<NavGraphContributor>(named("LoginNavGraph")) { LoginNavGraph }
}