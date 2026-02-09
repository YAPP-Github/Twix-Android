package com.twix.goal_manage.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.RepeatCycle
import com.twix.domain.model.goal.GoalSummary
import com.twix.ui.base.State
import java.time.LocalDate

@Immutable
data class GoalManageUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val referenceDate: LocalDate = LocalDate.now(), // 7일 달력을 생성하기 위한 레퍼런스 날짜
    val goalSummaries: List<GoalSummary> =
        listOf(
            GoalSummary(
                goalId = 1,
                name = "운동",
                icon = GoalIconType.EXERCISE,
                repeatCycle = RepeatCycle.DAILY,
                startDate = LocalDate.now(),
                endDate = LocalDate.now(),
            ),
            GoalSummary(
                goalId = 2,
                name = "물 마시기",
                icon = GoalIconType.EXERCISE,
                repeatCycle = RepeatCycle.DAILY,
                startDate = LocalDate.now(),
                endDate = null,
            ),
            GoalSummary(
                goalId = 3,
                name = "잠자기",
                icon = GoalIconType.EXERCISE,
                repeatCycle = RepeatCycle.DAILY,
                startDate = LocalDate.now(),
                endDate = LocalDate.now(),
            ),
            GoalSummary(
                goalId = 4,
                name = "운동",
                icon = GoalIconType.EXERCISE,
                repeatCycle = RepeatCycle.DAILY,
                startDate = LocalDate.now(),
                endDate = LocalDate.now(),
            ),
        ),
    val pendingGoalIds: Set<Long> = emptySet(), // 중복 요청 방지
    val openedMenuGoalId: Long? = null, // 팝업 현재 열린 goalId
    val endDialog: GoalDialogState? = null,
    val deleteDialog: GoalDialogState? = null,
) : State
