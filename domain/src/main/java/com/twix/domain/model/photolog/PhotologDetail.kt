package com.twix.domain.model.photolog

data class PhotologDetail(
    val comment: String,
    val goalId: Long,
    val imageUrl: String,
    val isMine: Boolean,
    val photologId: Long,
    val uploadedAt: String,
    val uploaderName: String,
    val verificationDate: String,
)
