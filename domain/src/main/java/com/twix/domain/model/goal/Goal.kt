package com.twix.domain.model.goal

import com.twix.domain.model.enums.RepeatCycle

data class Goal(
    val goalId: Long,
    val name: String,
    val icon: String,
    val repeatCycle: RepeatCycle,
    val myCompleted: Boolean,
    val partnerCompleted: Boolean,
    val myVerification: GoalVerification?, // API에서 object로 내려오지만 "없을 수도" 있게 설계 추천
    val partnerVerification: GoalVerification?,
)
