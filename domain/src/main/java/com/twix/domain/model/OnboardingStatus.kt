package com.twix.domain.model

enum class OnboardingStatus {
    COUPLE_CONNECTION,
    PROFILE_SETUP,
    ANNIVERSARY_SETUP,
    COMPLETED,
    ;

    companion object {
        fun from(status: String): OnboardingStatus =
            when (status) {
                "COUPLE_CONNECTION" -> COUPLE_CONNECTION
                "PROFILE_SETUP" -> PROFILE_SETUP
                "ANNIVERSARY_SETUP" -> ANNIVERSARY_SETUP
                "COMPLETED" -> COMPLETED
                else -> throw IllegalArgumentException(UNKNOWN_STATUS.format(status))
            }

        private const val UNKNOWN_STATUS = "UNKNOWN_STATUS: %s"
    }
}
