package com.twix.network.di

import com.twix.network.BuildConfig
import com.twix.network.HttpClientProvider
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

internal val httpClientModule =
    module {

        single<HttpClient> {
            HttpClientProvider.createHttpClient(
                baseUrl = BuildConfig.BASE_URL,
                isDebug = BuildConfig.DEBUG,
            )
        }

        single<Ktorfit> {
            Ktorfit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .httpClient(get<HttpClient>())
                .build()
        }
    }
