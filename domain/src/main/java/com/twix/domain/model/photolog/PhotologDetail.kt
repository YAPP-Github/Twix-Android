package com.twix.domain.model.photolog

data class PhotologDetail(
    val photologId: Long?,
    val goalId: Long,
    val imageUrl: String?,
    val comment: String?,
    val verificationDate: String?,
    val isMine: Boolean,
    val uploaderName: String?,
    val uploadedAt: String?,
)
