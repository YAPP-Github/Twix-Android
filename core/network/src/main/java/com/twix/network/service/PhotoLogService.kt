package com.twix.network.service

import com.twix.network.model.response.photo.model.PhotoLogUploadUrlResponse
import com.twix.network.model.response.photolog.PhotoLogResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query

interface PhotoLogService {
    @GET("api/v1/photologs/upload-url")
    suspend fun getUploadUrl(
        @Query("goalId") goalId: Long,
    ): PhotoLogUploadUrlResponse

    @GET("api/v1/photologs/goals/{goalId}")
    suspend fun fetchPhotoLogs(
        @Path("goalId") goalId: Long,
    ): PhotoLogResponse
}
