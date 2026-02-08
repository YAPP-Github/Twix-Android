package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.photolog.PhotologDetail
import com.twix.util.RelativeTimeFormatter
import java.time.LocalDate

@Immutable
data class PhotologDetailUiModel(
    val nickName: String = "",
    val goalId: Long = -1,
    val photologId: Long? = null,
    val imageUrl: String? = null,
    val comment: String? = null,
    val verificationDate: LocalDate? = null,
    val uploaderName: String? = null,
    val reaction: GoalReactionType? = null,
    val uploadedAt: String = "",
) {
    val isCertificated: Boolean
        get() =
            goalId != -1L &&
                photologId != null &&
                imageUrl != null &&
                comment != null &&
                verificationDate != null &&
                uploaderName != null &&
                uploadedAt.isNotEmpty()
}

fun PhotologDetail.toUiModel(nickName: String) =
    PhotologDetailUiModel(
        nickName = nickName,
        photologId = photologId,
        goalId = goalId,
        imageUrl = imageUrl,
        comment = comment,
        verificationDate = verificationDate,
        uploaderName = uploaderName,
        uploadedAt = uploadedAt?.let { RelativeTimeFormatter.format(it) } ?: "",
    )
