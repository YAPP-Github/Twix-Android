package com.twix.network.service

import com.twix.network.model.request.LoginRequest
import com.twix.network.model.request.RefreshRequest
import com.twix.network.model.response.RefreshResponse
import com.twix.network.model.response.onboarding.LoginResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {
    @POST("api/v1/auth/google/token")
    suspend fun googleLogin(
        @Body request: LoginRequest,
    ): LoginResponse

    @POST("api/v1/auth/refresh")
    suspend fun refresh(
        @Body request: RefreshRequest,
    ): RefreshResponse
}
