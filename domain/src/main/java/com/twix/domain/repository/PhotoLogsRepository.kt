package com.twix.domain.repository

import com.twix.domain.model.photolog.PhotoLogs
import com.twix.result.AppResult

interface PhotoLogsRepository {
    suspend fun fetchPhotoLogs(goalId: Long): AppResult<PhotoLogs>
}
