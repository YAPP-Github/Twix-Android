package com.twix.data.repository

import com.twix.domain.model.InviteCode
import com.twix.domain.model.OnboardingStatus
import com.twix.domain.repository.OnBoardingRepository
import com.twix.network.model.request.AnniversaryRequest
import com.twix.network.model.request.CoupleConnectionRequest
import com.twix.network.model.request.ProfileRequest
import com.twix.network.service.OnboardingService

class DefaultOnboardingRepository(
    private val service: OnboardingService,
) : OnBoardingRepository {
    override suspend fun fetchInviteCode(): InviteCode {
        val response = service.fetchInviteCode()
        return InviteCode(response.inviteCode)
    }

    override suspend fun fetchOnboardingStatus(): OnboardingStatus {
        val response = service.fetchOnBoardingStatus()
        return OnboardingStatus.from(response.status)
    }

    override suspend fun anniversarySetup(request: String) {
        service.anniversarySetup(AnniversaryRequest(request))
    }

    override suspend fun coupleConnection(request: String) {
        service.coupleConnection(CoupleConnectionRequest(request))
    }

    override suspend fun profileSetup(request: String) {
        service.profileSetup(ProfileRequest(request))
    }
}
