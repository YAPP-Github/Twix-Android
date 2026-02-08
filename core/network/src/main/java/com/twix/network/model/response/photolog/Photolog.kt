package com.twix.network.model.response.photolog

import kotlinx.serialization.Serializable

@Serializable
data class Photolog(
    val comment: String,
    val goalId: Long,
    val imageUrl: String,
    val isMine: Boolean,
    val photologId: Long,
    val uploadedAt: String,
    val uploaderName: String,
    val verificationDate: String,
)
