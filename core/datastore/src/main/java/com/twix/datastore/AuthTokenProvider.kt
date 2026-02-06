package com.twix.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import com.twix.token.TokenProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AuthTokenProvider(
    context: Context,
    scope: CoroutineScope,
) : TokenProvider {
    private val dataStore: DataStore<AuthConfigure> = context.authDataStore

    private val tokenState =
        dataStore.data
            .stateIn(
                scope = scope,
                started = SharingStarted.Eagerly,
                initialValue = AuthConfigure(),
            )

    override val accessToken: String
        get() = tokenState.value.accessToken

    override val refreshToken: String
        get() = tokenState.value.refreshToken

    override suspend fun saveToken(
        accessToken: String,
        refreshToken: String,
    ) {
        dataStore.updateData {
            it.copy(accessToken = accessToken, refreshToken = refreshToken)
        }
    }

    override suspend fun clear() {
        dataStore.updateData {
            it.copy(accessToken = "", refreshToken = "")
        }
    }
}
