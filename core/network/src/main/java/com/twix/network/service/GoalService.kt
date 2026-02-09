package com.twix.network.service

import com.twix.network.model.request.goal.model.CreateGoalRequest
import com.twix.network.model.response.goal.model.CreateGoalResponse
import com.twix.network.model.response.goal.model.GoalListResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Query

interface GoalService {
    @GET("api/v1/goals")
    suspend fun fetchGoals(
        @Query("date") date: String,
    ): GoalListResponse

    @POST("api/v1/goals")
    suspend fun createGoal(
        @Body body: CreateGoalRequest,
    ): CreateGoalResponse
}
