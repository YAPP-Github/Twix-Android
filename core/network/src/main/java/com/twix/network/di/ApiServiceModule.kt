package com.twix.network.di

import com.twix.network.service.OnboardingService
import com.twix.network.service.createOnboardingService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val apiServiceModule =
    module {
        single<OnboardingService> {
            get<Ktorfit>().createOnboardingService()
        }
    }
