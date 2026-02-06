package com.twix.data.di

import com.twix.data.repository.DefaultAuthRepository
import com.twix.data.repository.DefaultGoalRepository
import com.twix.data.repository.DefaultOnboardingRepository
import com.twix.domain.repository.AuthRepository
import com.twix.domain.repository.GoalRepository
import com.twix.domain.repository.OnBoardingRepository
import org.koin.dsl.module

internal val repositoryModule =
    module {
        single<OnBoardingRepository> {
            DefaultOnboardingRepository(get())
        }
        single<GoalRepository> {
            DefaultGoalRepository(get())
        }
        single<AuthRepository> {
            DefaultAuthRepository(get(), get())
        }
    }
