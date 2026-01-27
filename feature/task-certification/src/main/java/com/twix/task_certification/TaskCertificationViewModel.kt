package com.twix.task_certification

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationSideEffect
import com.twix.task_certification.model.TaskCertificationUiState
import com.twix.task_certification.model.TorchStatus
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TaskCertificationViewModel :
    BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.TakePicture -> {
                takePicture(intent.uri)
            }

            is TaskCertificationIntent.PickPicture -> {
                pickPicture(intent.uri)
            }

            is TaskCertificationIntent.ToggleLens -> {
                toggleLens()
            }

            is TaskCertificationIntent.ToggleFlash -> {
                toggleTorch()
            }
        }
    }

    private fun takePicture(uri: Uri?) {
        uri?.let { updatePickPicture(it) } ?: viewModelScope.launch {
            emitSideEffect(
                TaskCertificationSideEffect.ImageCaptureFailException,
            )
        }
    }

    private fun pickPicture(uri: Uri?) {
        uri?.let { updatePickPicture(uri) } ?: viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ImagePickFailException)
        }
    }

    private fun updatePickPicture(uri: Uri) {
        reduce { updateCapturedImage(uri) }
        if (uiState.value.torch == TorchStatus.On) {
            reduce { toggleTorch() }
        }
    }

    private fun toggleLens() {
        reduce { toggleLens() }
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
    }
}
