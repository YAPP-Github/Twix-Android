package com.twix.onboarding.model

import com.twix.ui.base.Intent

sealed interface OnBoardingIntent : Intent {
    data class WriteNickName(
        val value: String,
    ) : OnBoardingIntent

    data object SubmitNickName : OnBoardingIntent
}
