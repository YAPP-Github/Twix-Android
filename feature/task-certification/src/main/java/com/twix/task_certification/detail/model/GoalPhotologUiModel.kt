package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.photolog.GoalPhotolog
import com.twix.domain.model.photolog.PhotologDetail
import com.twix.util.RelativeTimeFormatter

@Immutable
data class GoalPhotologUiModel(
    val goalId: Long = -1,
    val goalName: String = "",
    val icon: GoalIconType = GoalIconType.DEFAULT,
    val myPhotolog: PhotologDetail? = null,
    val partnerPhotolog: PhotologDetail? = null,
) {
    val isCertificated: Boolean
        get() = myPhotolog != null

    val isPartnerCertificated: Boolean
        get() = partnerPhotolog != null

    val myUpdatedDate: String
        get() = myPhotolog?.uploadedAt?.let { RelativeTimeFormatter.format(it) } ?: ""

    val partnerUpdatedDate: String
        get() = partnerPhotolog?.uploadedAt?.let { RelativeTimeFormatter.format(it) } ?: ""

    fun updatePartnerReaction(reaction: GoalReactionType): GoalPhotologUiModel =
        copy(partnerPhotolog = partnerPhotolog?.updateReaction(reaction))
}

fun GoalPhotolog.toUiModel(): GoalPhotologUiModel =
    GoalPhotologUiModel(
        goalId = goalId,
        goalName = goalName,
        icon = icon,
        myPhotolog = myPhotolog,
        partnerPhotolog = partnerPhotolog,
    )
