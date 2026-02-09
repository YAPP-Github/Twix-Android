package com.twix.domain.repository

import com.twix.domain.model.goal.CreateGoalParam
import com.twix.domain.model.goal.CreatedGoal
import com.twix.domain.model.goal.GoalList
import com.twix.result.AppResult

interface GoalRepository {
    suspend fun fetchGoalList(date: String): AppResult<GoalList>

    suspend fun createGoal(param: CreateGoalParam): AppResult<CreatedGoal>
}
