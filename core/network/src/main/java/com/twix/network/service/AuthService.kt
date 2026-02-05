package com.twix.network.service

import com.twix.network.model.request.RefreshRequest
import com.twix.network.model.response.RefreshResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthService {
    @POST("auth/refresh")
    suspend fun refresh(
        @Body request: RefreshRequest,
    ): RefreshResponse
}
