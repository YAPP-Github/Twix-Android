package com.twix.navigation.serializer

import kotlinx.serialization.Serializable

@Serializable
data class TaskCertificationSerializer(
    val goalId: Long,
    val goalName: String,
    val nickname: String,
    val photologId: Long,
    val imageUrl: String,
    val comment: String?,
)
