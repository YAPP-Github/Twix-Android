package com.twix.network.service

import com.twix.network.model.response.photolog.PhotoLogResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface PhotoLogsService {
    @GET("/api/v1/photologs/goals/{goalId}")
    suspend fun fetchPhotoLogs(
        @Path("goalId") goalId: Long,
    ): PhotoLogResponse
}
