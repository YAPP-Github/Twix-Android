package com.twix.domain.model.goal

import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.RepeatCycle

data class Goal(
    val goalId: Long,
    val name: String,
    val icon: GoalIconType,
    val repeatCycle: RepeatCycle,
    val myCompleted: Boolean,
    val partnerCompleted: Boolean,
    val myVerification: GoalVerification?,
    val partnerVerification: GoalVerification?,
)
