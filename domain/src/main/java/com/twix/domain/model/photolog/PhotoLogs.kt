package com.twix.domain.model.photolog

data class PhotoLogs(
    val goalId: Long,
    val myNickname: String,
    val partnerNickname: String,
    val goalTitle: String,
    val photologDetails: List<PhotologDetail>,
)
