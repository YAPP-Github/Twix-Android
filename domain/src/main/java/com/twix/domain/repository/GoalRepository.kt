package com.twix.domain.repository

import com.twix.domain.model.goal.GoalList
import com.twix.result.AppResult

interface GoalRepository {
    suspend fun fetchGoalList(date: String): AppResult<GoalList>
}
