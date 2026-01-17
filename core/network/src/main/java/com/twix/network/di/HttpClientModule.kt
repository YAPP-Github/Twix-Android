package com.twix.network.di

import com.twix.network.BuildConfig
import com.twix.network.HttpClientProvider
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

internal val HttpClientModule =
    module {
        val baseUrl =
            if (BuildConfig.DEBUG) {
                BuildConfig.DEV_BASE_URL
            } else {
                BuildConfig.PROD_BASE_URL
            }

        single<HttpClient> {
            HttpClientProvider.createHttpClient(
                baseUrl = baseUrl,
                isDebug = BuildConfig.DEBUG,
            )
        }

        single {
            Ktorfit
                .Builder()
                .baseUrl(baseUrl)
                .httpClient(get<HttpClient>())
                .build()
        }
    }
