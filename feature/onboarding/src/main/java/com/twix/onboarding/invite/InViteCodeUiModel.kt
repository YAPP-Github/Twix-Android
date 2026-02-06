package com.twix.onboarding.invite

import androidx.compose.runtime.Immutable
import com.twix.domain.model.invitecode.InviteCode

@Immutable
data class InViteCodeUiModel(
    val myInviteCode: String = "",
    val partnerInviteCode: String = "",
    val isValid: Boolean = false,
) {
    fun updateMyInviteCode(value: String): InViteCodeUiModel = copy(myInviteCode = value)

    fun updatePartnerInviteCode(value: String): InViteCodeUiModel =
        InviteCode
            .create(value)
            .fold(
                onSuccess = { copy(partnerInviteCode = value, isValid = true) },
                onFailure = { copy(partnerInviteCode = value, isValid = false) },
            )
}
