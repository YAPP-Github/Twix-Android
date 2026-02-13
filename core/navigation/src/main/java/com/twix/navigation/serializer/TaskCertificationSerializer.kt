package com.twix.navigation.serializer

import kotlinx.serialization.Serializable

@Serializable
data class TaskCertificationSerializer(
    val nickname: String,
    val goalName: String,
    val photologId: Long,
    val imageUrl: String,
    val comment: String?,
)
