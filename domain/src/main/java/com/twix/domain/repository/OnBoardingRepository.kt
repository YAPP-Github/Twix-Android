package com.twix.domain.repository

import com.twix.domain.model.OnboardingStatus
import com.twix.domain.model.invitecode.InviteCode

interface OnBoardingRepository {
    suspend fun anniversarySetup(request: String)

    suspend fun coupleConnection(request: String)

    suspend fun profileSetup(request: String)

    suspend fun fetchInviteCode(): InviteCode

    suspend fun fetchOnboardingStatus(): OnboardingStatus
}
