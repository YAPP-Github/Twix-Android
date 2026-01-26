package com.twix.domain.model

@JvmInline
value class InviteCode(
    val value: String,
) {
    init {
        require(value.length == INVITE_CODE_LENGTH) { INVALID_INVITE_CODE_EXCEPTION }
        require(INVITE_CODE_REGEX.matches(value)) { INVALID_INVITE_CODE_EXCEPTION }
    }

    companion object {
        private val INVITE_CODE_REGEX = Regex("^[A-Z0-9]+$")
        private const val INVITE_CODE_LENGTH = 8
        private const val INVALID_INVITE_CODE_EXCEPTION =
            "InviteCode must be 8 characters of uppercase letters and digits"
    }
}
