package com.twix.onboarding.invite

import androidx.compose.runtime.Immutable
import com.twix.domain.model.invitecode.InviteCode

@Immutable
data class InViteCodeUiModel(
    val myInviteCode: String = "",
    val inviteCode: String = "",
    val isValid: Boolean = false,
) {
    fun updateMyInviteCode(value: String): InViteCodeUiModel = copy(myInviteCode = value)

    fun updateInviteCode(value: String): InViteCodeUiModel =
        InviteCode
            .create(value)
            .fold(
                onSuccess = { copy(inviteCode = value, isValid = true) },
                onFailure = { copy(inviteCode = value, isValid = false) },
            )
}
