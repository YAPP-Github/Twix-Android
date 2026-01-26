package com.twix.data.di

import com.twix.data.repository.DefaultOnboardingRepository
import com.twix.domain.repository.OnBoardingRepository
import org.koin.dsl.module

internal val repositoryModule =
    module {
        single<OnBoardingRepository> {
            DefaultOnboardingRepository(get())
        }
    }
