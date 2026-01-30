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

            is TaskCertificationIntent.ToggleLens -> {
                toggleLens()
            }

            is TaskCertificationIntent.ToggleFlash -> {
                toggleTorch()
            }
        }
    }

    private fun takePicture(uri: Uri?) {
        uri?.let {
            reduce { updateCapturedImage(uri) }
            if (uiState.value.torch == TorchStatus.On) {
                reduce { toggleTorch() }
            }
        } ?: run { onFailureCapture() }
    }

    private fun onFailureCapture() {
        viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
        }
    }

    private fun toggleLens() {
        reduce { toggleLens() }
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
    }
}
