package com.twix.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CoupleConnectionReqeust(
    val inviteCode: String,
)
