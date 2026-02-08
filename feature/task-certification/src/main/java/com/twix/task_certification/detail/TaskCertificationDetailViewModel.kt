package com.twix.task_certification.detail

import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.repository.PhotoLogsRepository
import com.twix.task_certification.R
import com.twix.task_certification.detail.model.TaskCertificationDetailIntent
import com.twix.task_certification.detail.model.TaskCertificationDetailSideEffect
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState
import com.twix.ui.base.BaseViewModel

class TaskCertificationDetailViewModel(
    private val photologRepository: PhotoLogsRepository,
) : BaseViewModel<TaskCertificationDetailUiState, TaskCertificationDetailIntent, TaskCertificationDetailSideEffect>(
        TaskCertificationDetailUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationDetailIntent) {
        when (intent) {
            is TaskCertificationDetailIntent.InitGoal -> {
                fetchPhotolog(intent.goalId, intent.goalTitle)
            }
        }
    }

    private fun fetchPhotolog(
        goalId: Long,
        goalTitle: String,
    ) {
        launchResult(
            block = { photologRepository.fetchPhotoLogs(goalId) },
            onSuccess = { reduce { copy(photoLogs = it, goalTitle = goalTitle) } },
            onError = {
                emitSideEffect(
                    TaskCertificationDetailSideEffect.ShowToast(
                        R.string.fetch_photolog_fail,
                        ToastType.ERROR,
                    ),
                )
            },
        )
    }
}
