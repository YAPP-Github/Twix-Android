package com.twix.domain.login

interface LoginProvider {
    suspend fun login(): LoginResult

    suspend fun logout(): Result<Unit>
}
