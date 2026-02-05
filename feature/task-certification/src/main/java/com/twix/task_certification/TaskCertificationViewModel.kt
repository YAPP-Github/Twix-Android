package com.twix.task_certification

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationSideEffect
import com.twix.task_certification.model.TaskCertificationUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TaskCertificationViewModel :
    BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.TakePicture -> {
                reducePicture(intent.uri)
            }

            is TaskCertificationIntent.PickPicture -> {
                reducePicture(intent.uri)
            }

            is TaskCertificationIntent.ToggleLens -> {
                reduceLens()
            }

            is TaskCertificationIntent.ToggleTorch -> {
                reduceTorch()
            }

            is TaskCertificationIntent.RetakePicture -> {
                setupRetake()
            }
        }
    }

    private fun reducePicture(uri: Uri?) {
        uri?.let {
            reduce { updatePicture(uri) }
        } ?: run { onFailureCapture() }
    }

    private fun onFailureCapture() {
        viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ShowImageCaptureFailToast)
        }
    }

    private fun reduceLens() {
        reduce { toggleLens() }
    }

    private fun reduceTorch() {
        reduce { toggleTorch() }
    }

    private fun setupRetake() {
        reduce { removePicture() }
    }
}
