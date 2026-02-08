package com.twix.network.model.response.photolog

import kotlinx.serialization.Serializable
import java.time.LocalDate
import kotlin.time.Instant

@Serializable
data class PhotologDetailResponse(
    val photologId: Long?,
    val goalId: Long,
    val imageUrl: String?,
    val comment: String?,
    val verificationDate: LocalDate?,
    val isMine: Boolean,
    val uploaderName: String?,
    val uploadedAt: Instant?,
)
