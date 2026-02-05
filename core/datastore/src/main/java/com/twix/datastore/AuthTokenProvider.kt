package com.twix.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import com.twix.token.TokenProvider
import kotlinx.coroutines.flow.first

class AuthTokenProvider(
    private val context: Context,
) : TokenProvider {
    private val dataStore: DataStore<AuthConfigure>
        get() = context.authDataStore

    override suspend fun accessToken(): String = dataStore.data.first().accessToken

    override suspend fun refreshToken(): String = dataStore.data.first().refreshToken

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
