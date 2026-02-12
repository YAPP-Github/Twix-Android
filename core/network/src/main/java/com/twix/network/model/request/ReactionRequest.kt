package com.twix.network.model.request

import com.twix.domain.model.enums.GoalReactionType
import kotlinx.serialization.Serializable

@Serializable
data class ReactionRequest(
    val reaction: String,
)

fun GoalReactionType.toRequest(): ReactionRequest {
    val reaction =
        when (this) {
            GoalReactionType.HAPPY -> "ICON_HAPPY"
            GoalReactionType.TROUBLE -> "ICON_TROUBLE"
            GoalReactionType.LOVE -> "ICON_LOVE"
            GoalReactionType.DOUBT -> "ICON_DOUBT"
            GoalReactionType.FUCK -> "ICON_FUCK"
        }

    return ReactionRequest(reaction)
}
