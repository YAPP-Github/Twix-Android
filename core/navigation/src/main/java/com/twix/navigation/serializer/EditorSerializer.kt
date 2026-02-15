package com.twix.navigation.serializer

import com.twix.navigation.NavRoutes
import kotlinx.serialization.Serializable

@Serializable
data class EditorSerializer(
    val goalId: Long,
    val goalName: String,
    val nickname: String,
    val photologId: Long,
    val imageUrl: String,
    val comment: String?,
)

@Serializable
data class DetailSerializer(
    val goalId: Long,
    val from: NavRoutes.TaskCertificationRoute.From,
    val photologId: Long = -1,
    val selectedDate: String = "",
    val comment: String = "",
)
