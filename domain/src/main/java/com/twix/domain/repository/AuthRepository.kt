package com.twix.domain.repository

import com.twix.domain.login.LoginType

interface AuthRepository {
    suspend fun login(
        idToken: String,
        type: LoginType,
    )
}
