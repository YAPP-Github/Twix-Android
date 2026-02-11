package com.twix.network.model.response.photolog

import kotlinx.serialization.Serializable

@Serializable
data class PhotoLogResponse(
    val goalId: Long,
    val myNickname: String,
    val partnerNickname: String,
    val goalTitle: String,
    val photologs: List<PhotologDetailResponse>?,
)
