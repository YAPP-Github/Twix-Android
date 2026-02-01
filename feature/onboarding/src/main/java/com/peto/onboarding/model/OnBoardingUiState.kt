package com.peto.onboarding.model

import androidx.compose.runtime.Immutable
import com.peto.onboarding.profile.ProfileUiModel
import com.twix.ui.base.State

@Immutable
data class OnBoardingUiState(
    val profile: ProfileUiModel = ProfileUiModel(),
) : State {
    fun updateNickName(value: String) = copy(profile = profile.updateNickname(value))
}
