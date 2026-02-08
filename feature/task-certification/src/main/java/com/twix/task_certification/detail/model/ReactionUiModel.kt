package com.twix.task_certification.detail.model

import com.twix.domain.model.enums.GoalReactionType
import com.twix.designsystem.R as DesR

enum class ReactionUiModel(
    val type: GoalReactionType,
    val imageResources: Int,
) {
    HAPPY(GoalReactionType.HAPPY, DesR.drawable.ic_keepi_happy),
    TROUBLE(GoalReactionType.TROUBLE, DesR.drawable.ic_keepi_trouble),
    LOVE(GoalReactionType.LOVE, DesR.drawable.ic_keepi_love),
    DOUBT(GoalReactionType.DOUBT, DesR.drawable.ic_keepi_doubt),
    FUCK(GoalReactionType.FUCK, DesR.drawable.ic_keepi_fuck),
}
