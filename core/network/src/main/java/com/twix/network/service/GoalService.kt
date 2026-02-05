package com.twix.network.service

import com.twix.network.model.response.goal.model.GoalListResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface GoalService {
    @GET("/api/v1/goals")
    suspend fun fetchGoals(
        @Query("date") date: String,
    ): GoalListResponse
}
