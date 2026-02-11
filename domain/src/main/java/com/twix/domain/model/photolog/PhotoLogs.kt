package com.twix.domain.model.photolog

data class PhotoLogs(
    val goalId: Long,
    val goalTitle: String,
    val myNickname: String,
    val partnerNickname: String,
    val photologDetails: List<PhotologDetail>,
)
