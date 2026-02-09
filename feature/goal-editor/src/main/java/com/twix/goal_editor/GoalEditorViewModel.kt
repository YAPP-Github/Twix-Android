package com.twix.goal_editor

import androidx.lifecycle.viewModelScope
import com.twix.designsystem.R
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.enums.GoalIconType
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.model.enums.RepeatCycle
import com.twix.domain.model.goal.CreateGoalParam
import com.twix.domain.model.goal.Goal
import com.twix.domain.model.goal.GoalVerification
import com.twix.domain.model.goal.UpdateGoalParam
import com.twix.domain.repository.GoalRepository
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.base.BaseViewModel
import com.twix.util.bus.GoalRefreshBus
import kotlinx.coroutines.launch
import java.time.LocalDate

class GoalEditorViewModel(
    private val goalRepository: GoalRepository,
    private val goalRefreshBus: GoalRefreshBus,
) : BaseViewModel<GoalEditorUiState, GoalEditorIntent, GoalEditorSideEffect>(
        GoalEditorUiState(),
    ) {
    override suspend fun handleIntent(intent: GoalEditorIntent) {
        when (intent) {
            is GoalEditorIntent.Save -> save(intent.id)
            is GoalEditorIntent.SetIcon -> setIcon(intent.icon)
            is GoalEditorIntent.SetEndDate -> setEndDate(intent.endDate)
            is GoalEditorIntent.SetRepeatCount -> setRepeatCount(intent.repeatCount)
            is GoalEditorIntent.SetRepeatType -> setRepeatType(intent.repeatCycle)
            is GoalEditorIntent.SetStartDate -> setStartDate(intent.startDate)
            is GoalEditorIntent.SetTitle -> setTitle(intent.title)
            is GoalEditorIntent.SetEndDateEnabled -> setEndDateEnabled(intent.enabled)
            is GoalEditorIntent.InitGoal -> initGoal(intent.id)
        }
    }

    private fun setIcon(icon: GoalIconType) {
        reduce { copy(selectedIcon = icon) }
    }

    private fun setTitle(title: String) {
        if (title.isBlank()) return

        reduce { copy(goalTitle = title) }
    }

    private fun setRepeatType(repeatCycle: RepeatCycle) {
        reduce { copy(selectedRepeatCycle = repeatCycle, repeatCount = 1) }
    }

    private fun setRepeatCount(repeatCount: Int) {
        if (repeatCount <= 0) return

        reduce { copy(repeatCount = repeatCount) }
    }

    private fun setStartDate(startDate: LocalDate) {
        reduce { copy(startDate = startDate) }
    }

    private fun setEndDate(endDate: LocalDate) {
        reduce { copy(endDate = endDate) }
    }

    private fun setEndDateEnabled(enabled: Boolean) {
        reduce { copy(endDateEnabled = enabled) }
    }

    private fun save(id: Long) {
        if (!currentState.isEnabled) return

        if (currentState.endDateEnabled && currentState.endDate.isBefore(currentState.startDate)) {
            viewModelScope.launch {
                emitSideEffect(GoalEditorSideEffect.ShowToast(R.string.toast_end_date_before_start_date, ToastType.ERROR))
            }
            return
        }

        if (id == -1L) {
            launchResult(
                block = { goalRepository.createGoal(currentState.toCreateParam()) },
                onSuccess = {
                    goalRefreshBus.notifyChanged()
                    tryEmitSideEffect(GoalEditorSideEffect.NavigateToHome)
                },
                onError = { emitSideEffect(GoalEditorSideEffect.ShowToast(R.string.toast_create_goal_failed, ToastType.ERROR)) },
            )
        } else {
            launchResult(
                block = { goalRepository.updateGoal(currentState.toUpdateParam(id)) },
                onSuccess = {
                    goalRefreshBus.notifyChanged()
                    tryEmitSideEffect(GoalEditorSideEffect.NavigateToHome)
                },
                onError = { emitSideEffect(GoalEditorSideEffect.ShowToast(R.string.toast_update_goal_failed, ToastType.ERROR)) },
            )
        }
    }

    private fun initGoal(id: Long) {
        val goal =
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
            )

        reduce {
            copy(
                goalTitle = goal.name,
                selectedIcon = goal.icon,
                selectedRepeatCycle = goal.repeatCycle,
                repeatCount = 4,
                startDate = LocalDate.now(),
                endDate = LocalDate.now().plusWeeks(1),
                endDateEnabled = false,
            )
        }
    }

    private fun GoalEditorUiState.toCreateParam(): CreateGoalParam =
        CreateGoalParam(
            name = goalTitle.trim(),
            icon = selectedIcon,
            repeatCycle = selectedRepeatCycle,
            repeatCount = repeatCount,
            startDate = startDate,
            endDate = if (endDateEnabled) endDate else null,
        )

    private fun GoalEditorUiState.toUpdateParam(id: Long): UpdateGoalParam =
        UpdateGoalParam(
            goalId = id,
            name = goalTitle.trim(),
            icon = selectedIcon,
            repeatCycle = selectedRepeatCycle,
            repeatCount = repeatCount,
            endDate = if (endDateEnabled) endDate else null,
        )
}
