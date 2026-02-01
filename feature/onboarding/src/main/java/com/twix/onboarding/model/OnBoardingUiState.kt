package com.twix.onboarding.model

import androidx.compose.runtime.Immutable
import com.twix.onboarding.profile.ProfileUiModel
import com.twix.ui.base.State

@Immutable
data class OnBoardingUiState(
    val profile: ProfileUiModel = ProfileUiModel(),
) : State {
    val isValidNickName: Boolean
        get() = profile.isValid

    fun updateNickName(value: String) = copy(profile = profile.updateNickname(value))
}
