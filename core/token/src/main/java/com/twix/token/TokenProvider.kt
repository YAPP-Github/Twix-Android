package com.twix.token

interface TokenProvider {
    suspend fun accessToken(): String

    suspend fun refreshToken(): String

    suspend fun saveToken(
        accessToken: String,
        refreshToken: String,
    )

    suspend fun clear()
}
