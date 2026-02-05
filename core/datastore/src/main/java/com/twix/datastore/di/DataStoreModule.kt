package com.twix.datastore.di

import com.twix.datastore.AuthTokenProvider
import com.twix.token.TokenProvider
import org.koin.dsl.module

val dataStoreModule =
    module {
        single<TokenProvider> { AuthTokenProvider(get()) }
    }
