package com.twix.domain.model.enums

enum class GoalReactionType {
    HAPPY,
    TROUBLE,
    LOVE,
    DOUBT,
    FUCK,
    ;

    companion object {
        fun fromApi(reaction: String?): GoalReactionType? =
            when (reaction) {
                "ICON_HAPPY" -> HAPPY
                "ICON_TROUBLE" -> TROUBLE
                "ICON_LOVE" -> LOVE
                "ICON_DOUBT" -> DOUBT
                "ICON_FUCK" -> FUCK
                else -> null
            }
    }
}
