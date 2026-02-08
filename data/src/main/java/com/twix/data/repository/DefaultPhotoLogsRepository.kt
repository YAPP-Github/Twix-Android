package com.twix.data.repository

import com.twix.domain.model.photolog.PhotoLogs
import com.twix.domain.repository.PhotoLogsRepository
import com.twix.network.execute.safeApiCall
import com.twix.network.model.response.photolog.mapper.toDomain
import com.twix.network.service.PhotoLogsService
import com.twix.result.AppResult

class DefaultPhotoLogsRepository(
    private val service: PhotoLogsService,
) : PhotoLogsRepository {
    override suspend fun fetchPhotoLogs(goalId: Long): AppResult<PhotoLogs> = safeApiCall { service.fetchPhotoLogs(goalId).toDomain() }
}
