package com.twix.domain.repository

import com.twix.domain.login.LoginType
import com.twix.result.AppResult

interface AuthRepository {
    suspend fun login(
        idToken: String,
        type: LoginType,
    )

    suspend fun logout(): AppResult<Unit>

    suspend fun withdrawAccount(): AppResult<Unit>
}
