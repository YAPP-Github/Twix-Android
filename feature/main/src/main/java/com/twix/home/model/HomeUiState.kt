package com.twix.home.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.enums.RepeatCycle
import com.twix.domain.model.goal.Goal
import com.twix.domain.model.goal.GoalList
import com.twix.domain.model.goal.GoalVerification
import com.twix.ui.base.State
import java.time.LocalDate
import java.time.YearMonth

@Immutable
data class HomeUiState(
    val month: YearMonth = YearMonth.now(),
    val visibleDate: LocalDate = LocalDate.now(), // 홈 화면 상단에 존재하는 월, 년 텍스트를 위한 상태 변수
    val selectedDate: LocalDate = LocalDate.now(),
    val referenceDate: LocalDate = LocalDate.now(), // 7일 달력을 생성하기 위한 레퍼런스 날짜
    val goalList: GoalList =
        GoalList(
            goals =
                listOf(
                    Goal(
                        goalId = 1,
                        name = "운동",
                        icon = GoalIconType.EXERCISE,
                        repeatCycle = RepeatCycle.WEEKLY,
                        myCompleted = true,
                        partnerCompleted = false,
                        myVerification = null,
                        partnerVerification = null,
                    ),
                    Goal(
                        goalId = 2,
                        name = "운동",
                        icon = GoalIconType.EXERCISE,
                        repeatCycle = RepeatCycle.WEEKLY,
                        myCompleted = true,
                        partnerCompleted = false,
                        myVerification =
                            GoalVerification(
                                photologId = 1,
                                imageUrl = "https://picsum.photos/400/300",
                                comment = null,
                                reaction = GoalReactionType.LOVE,
                                uploadedAt = "2023-05-05",
                            ),
                        partnerVerification = null,
                    ),
                    Goal(
                        goalId = 3,
                        name = "잠자기",
                        icon = GoalIconType.HEART,
                        repeatCycle = RepeatCycle.WEEKLY,
                        myCompleted = false,
                        partnerCompleted = true,
                        myVerification = null,
                        partnerVerification =
                            GoalVerification(
                                photologId = 1,
                                imageUrl = "https://picsum.photos/400/300",
                                comment = null,
                                reaction = GoalReactionType.LOVE,
                                uploadedAt = "2023-05-05",
                            ),
                    ),
                    Goal(
                        goalId = 4,
                        name = "밥무라",
                        icon = GoalIconType.DEFAULT,
                        repeatCycle = RepeatCycle.WEEKLY,
                        myCompleted = true,
                        partnerCompleted = true,
                        myVerification =
                            GoalVerification(
                                photologId = 1,
                                imageUrl = "https://picsum.photos/400/300",
                                comment = null,
                                reaction = GoalReactionType.LOVE,
                                uploadedAt = "2023-05-05",
                            ),
                        partnerVerification =
                            GoalVerification(
                                photologId = 1,
                                imageUrl = "https://picsum.photos/400/300",
                                comment = null,
                                reaction = GoalReactionType.LOVE,
                                uploadedAt = "2023-05-05",
                            ),
                    ),
                ),
        ),
) : State {
    val monthYear: String
        get() = "${visibleDate.month.value}월 ${visibleDate.year}"
}
