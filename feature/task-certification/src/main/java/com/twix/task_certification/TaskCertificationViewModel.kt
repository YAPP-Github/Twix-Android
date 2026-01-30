package com.twix.task_certification

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
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
                takePicture()
            }

            is TaskCertificationIntent.ToggleCamera -> {
                toggleCamera(intent.lifecycleOwner)
            }

            is TaskCertificationIntent.ToggleFlash -> {
                toggleTorch()
            }
        }
    }

    private fun takePicture() {
//        camera.takePicture(
//            onComplete = {
//                updateCapturedCImage(it)
//                unbindCamera()
//            },
//            onFailure = {
//                onFailureCapture()
//            },
//        )
    }

    private fun onFailureCapture() {
        viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
        }
    }

    private fun updateCapturedCImage(uri: Uri?) {
        uri?.let {
            reduce { updateCapturedImage(uri) }
            reduce { toggleTorch() }
        } ?: run {
            viewModelScope.launch {
                emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
            }
        }
    }

    private fun toggleCamera(lifecycleOwner: LifecycleOwner) {
        reduce { toggleLens() }
        // bindCamera(lifecycleOwner)
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
        // camera.toggleTorch(uiState.value.torch)
    }
}
