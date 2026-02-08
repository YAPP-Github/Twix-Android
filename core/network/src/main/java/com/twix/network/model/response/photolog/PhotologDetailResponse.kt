package com.twix.network.model.response.photolog

import kotlinx.serialization.Serializable

@Serializable
data class PhotologDetailResponse(
    val photologId: Long?,
    val goalId: Long,
    val imageUrl: String?,
    val comment: String?,
    val verificationDate: String?,
    val isMine: Boolean,
    val uploaderName: String?,
    val uploadedAt: String?,
)
