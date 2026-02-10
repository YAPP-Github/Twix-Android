package com.twix.task_certification.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.repository.PhotoLogRepository
import com.twix.navigation.NavRoutes
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.TaskCertificationDetailIntent
import com.twix.task_certification.detail.model.TaskCertificationDetailSideEffect
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState
import com.twix.task_certification.detail.model.toUiModel
import com.twix.ui.base.BaseViewModel
import com.twix.util.bus.GoalRefreshBus
import com.twix.util.bus.TaskCertificationRefreshBus
import kotlinx.coroutines.launch

class TaskCertificationDetailViewModel(
    private val photologRepository: PhotoLogRepository,
    private val taskCertificationRefreshBus: TaskCertificationRefreshBus,
    private val goalRefreshBus: GoalRefreshBus,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskCertificationDetailUiState, TaskCertificationDetailIntent, TaskCertificationDetailSideEffect>(
        TaskCertificationDetailUiState(),
    ) {
    private val goalId: Long =
        savedStateHandle[NavRoutes.TaskCertificationDetailRoute.ARG_GOAL_ID]
            ?: throw IllegalArgumentException(GOAL_ID_NOT_FOUND)

    init {
        fetchPhotolog()

        collectEventBus()
    }

    private fun collectEventBus() {
        viewModelScope.launch {
            taskCertificationRefreshBus.events.collect {
                fetchPhotolog()
                goalRefreshBus.notifyChanged()
            }
        }
    }

    override suspend fun handleIntent(intent: TaskCertificationDetailIntent) {
        when (intent) {
            is TaskCertificationDetailIntent.Reaction -> reduceReaction(intent.type)
            TaskCertificationDetailIntent.Sting -> TODO("찌르기 API 연동")
            TaskCertificationDetailIntent.SwipeCard -> reduceShownCard()
        }
    }

    private fun fetchPhotolog() {
        launchResult(
            block = { photologRepository.fetchPhotoLogs(goalId) },
            onSuccess = {
                reduce { copy(photoLogs = it.toUiModel(), goalTitle = goalTitle) }
            },
            onError = {
                emitSideEffect(
                    TaskCertificationDetailSideEffect.ShowToast(
                        R.string.task_certification_detail_fetch_photolog_fail,
                        ToastType.ERROR,
                    ),
                )
            },
        )
    }

    private fun reduceReaction(reaction: GoalReactionType) {
        reduce { updatePartnerReaction(reaction) }
    }

    private fun reduceShownCard() {
        reduce { toggleBetweenUs() }
    }

    companion object {
        private const val GOAL_ID_NOT_FOUND = "Goal Id Argument Not Found"
    }
}
