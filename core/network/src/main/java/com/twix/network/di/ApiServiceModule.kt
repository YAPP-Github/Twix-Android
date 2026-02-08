package com.twix.network.di

import com.twix.network.service.AuthService
import com.twix.network.service.GoalService
import com.twix.network.service.OnboardingService
import com.twix.network.service.PhotoLogsService
import com.twix.network.service.createAuthService
import com.twix.network.service.createGoalService
import com.twix.network.service.createOnboardingService
import com.twix.network.service.createPhotoLogsService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.dsl.module

internal val apiServiceModule =
    module {
        single<OnboardingService> {
            get<Ktorfit>().createOnboardingService()
        }
        single<AuthService> {
            get<Ktorfit>().createAuthService()
        }
        single<GoalService> {
            get<Ktorfit>().createGoalService()
        }
        single<PhotoLogsService> {
            get<Ktorfit>().createPhotoLogsService()
        }
    }
