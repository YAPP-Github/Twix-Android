package com.twix.domain.model.goal

data class GoalVerification(
    val photologId: Long,
    val imageUrl: String,
    val comment: String?,
    val reaction: String?,
    val uploadedAt: String,
)
