package com.twix.domain.repository

import com.twix.domain.model.photo.PhotoLogUploadInfo
import com.twix.result.AppResult

interface PhotoLogRepository {
    suspend fun getUploadUrl(goalId: Long): AppResult<PhotoLogUploadInfo>

    suspend fun uploadPhotoLogImage(
        goalId: Long,
        bytes: ByteArray,
        contentType: String,
    ): AppResult<String>
}
