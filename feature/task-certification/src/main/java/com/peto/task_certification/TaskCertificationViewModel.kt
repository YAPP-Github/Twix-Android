package com.peto.task_certification

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.peto.task_certification.camera.Camera
import com.peto.task_certification.model.CameraPreview
import com.peto.task_certification.model.CaptureStatus
import com.peto.task_certification.model.TaskCertificationIntent
import com.peto.task_certification.model.TaskCertificationSideEffect
import com.peto.task_certification.model.TaskCertificationUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskCertificationViewModel(
    private val camera: Camera,
) : BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    init {
        camera.surfaceRequests
            .map { request -> request?.let { CameraPreview(request) } }
            .onEach { preview ->
                reduce { copy(preview = preview) }
            }.launchIn(viewModelScope)
    }

    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.BindCamera -> {
                bindCamera(intent.lifecycleOwner)
            }

            is TaskCertificationIntent.TakePicture -> {
                takePicture()
            }
        }
    }

    private fun takePicture() {
        camera.takePicture(
            onComplete = {
                updateCapturedCImage(it)
                unbindCamera()
            },
            onFailure = {
                onFailureCapture()
            },
        )
    }

    private fun onFailureCapture() {
        viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
        }
    }

    private fun unbindCamera() {
        viewModelScope.launch {
            camera.unbind()
        }
    }

    private suspend fun bindCamera(lifecycleOwner: LifecycleOwner) {
        camera.bind(lifecycleOwner, uiState.value.lens)
    }

    private fun updateCapturedCImage(uri: Uri?) {
        uri?.let {
            reduce { updateCapturedCImage(uri) }
        } ?: run {
            viewModelScope.launch {
                emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
            }
        }
    }
}
