package com.twix.data.repository

import com.twix.domain.model.goal.GoalList
import com.twix.domain.repository.GoalRepository
import com.twix.network.execute.safeApiCall
import com.twix.network.model.response.goal.mapper.toDomain
import com.twix.network.service.GoalService
import com.twix.result.AppResult

class DefaultGoalRepository(
    private val service: GoalService,
) : GoalRepository {
    override suspend fun fetchGoalList(date: String): AppResult<GoalList> = safeApiCall { service.fetchGoals(date).toDomain() }
}
