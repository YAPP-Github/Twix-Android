package com.twix.domain.model.enums

enum class GoalIconType {
    DEFAULT,
    CLEAN,
    EXERCISE,
    BOOK,
    PENCIL,
    HEALTH,
    HEART,
    LAPTOP,
    ;

    companion object {
        fun fromApi(icon: String): GoalIconType =
            when (icon) {
                "ICON_DEFAULT" -> DEFAULT
                "ICON_CLEAN" -> CLEAN
                "ICON_EXERCISE" -> EXERCISE
                "ICON_BOOK" -> BOOK
                "ICON_PENCIL" -> PENCIL
                "ICON_HEALTH" -> HEALTH
                "ICON_HEART" -> HEART
                "ICON_LAPTOP" -> LAPTOP
                else -> DEFAULT
            }
    }
}
