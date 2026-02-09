package com.twix.network.service

import com.twix.network.model.response.photo.model.PhotoLogUploadUrlResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface PhotoLogService {
    @GET("api/v1/photologs/upload-url")
    suspend fun getUploadUrl(
        @Query("goalId") goalId: Long,
    ): PhotoLogUploadUrlResponse
}
