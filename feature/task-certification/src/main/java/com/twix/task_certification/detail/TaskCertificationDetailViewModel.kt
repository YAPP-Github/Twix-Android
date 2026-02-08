package com.twix.task_certification.detail

import androidx.lifecycle.viewModelScope
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.enums.GoalReactionType
import com.twix.domain.repository.PhotoLogsRepository
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.TaskCertificationDetailIntent
import com.twix.task_certification.detail.model.TaskCertificationDetailSideEffect
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState
import com.twix.task_certification.detail.model.toUiModel
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TaskCertificationDetailViewModel(
    private val photologRepository: PhotoLogsRepository,
) : BaseViewModel<TaskCertificationDetailUiState, TaskCertificationDetailIntent, TaskCertificationDetailSideEffect>(
        TaskCertificationDetailUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationDetailIntent) {
        when (intent) {
            is TaskCertificationDetailIntent.InitGoal -> fetchPhotolog(intent.goalId)
            is TaskCertificationDetailIntent.Reaction -> reduceReaction(intent.type)
            TaskCertificationDetailIntent.Sting -> TODO("찌르기 API 연동")
            TaskCertificationDetailIntent.Upload -> navigateToUpload()
        }
    }

    private fun fetchPhotolog(goalId: Long) {
        launchResult(
            block = { photologRepository.fetchPhotoLogs(goalId) },
            onSuccess = { reduce { copy(photoLogs = it.toUiModel(), goalTitle = goalTitle) } },
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

    private fun navigateToUpload() {
        viewModelScope.launch {
            val goalId = currentState.photoLogs.goalId
            emitSideEffect(TaskCertificationDetailSideEffect.NavigateToUpload(goalId))
        }
    }
}
