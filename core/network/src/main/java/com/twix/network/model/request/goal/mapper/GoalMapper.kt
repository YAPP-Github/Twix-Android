package com.twix.network.model.request.goal.mapper

import com.twix.domain.model.goal.CreateGoalParam
import com.twix.network.model.request.goal.model.CreateGoalRequest

fun CreateGoalParam.toRequest(): CreateGoalRequest =
    CreateGoalRequest(
        name = name,
        icon = icon.toApi(),
        repeatCycle = repeatCycle.toApi(),
        repeatCount = repeatCount,
        startDate = startDate.toString(),
        endDate = endDate?.toString(),
    )
