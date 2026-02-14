package com.twix.task_certification.detail.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.photolog.PhotoLogs

@Immutable
data class PhotoLogsUiModel(
    val targetDate: String = "",
    val myNickname: String = "",
    val partnerNickname: String = "",
    val goalPhotolog: List<GoalPhotologUiModel> = emptyList(),
) {
    private val goalCache: Map<Long, GoalPhotologUiModel>
        get() = goalPhotolog.associateBy { it.goalId }

    operator fun get(goalId: Long): GoalPhotologUiModel = goalCache[goalId] ?: GoalPhotologUiModel()

    fun updatePartnerReaction(
        goalId: Long,
        reaction: GoalReactionType,
    ): PhotoLogsUiModel =
        copy(
            goalPhotolog =
                goalPhotolog.map {
                    if (it.goalId == goalId) {
                        it.updatePartnerReaction(reaction)
                    } else {
                        it
                    }
                },
        )
}

fun PhotoLogs.toUiModel(): PhotoLogsUiModel =
    PhotoLogsUiModel(
        targetDate = targetDate,
        myNickname = myNickname,
        partnerNickname = partnerNickname,
        goalPhotolog = goals.map { it.toUiModel() },
    )
