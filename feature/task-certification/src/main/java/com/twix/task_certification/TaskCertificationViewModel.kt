package com.twix.task_certification

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.twix.task_certification.camera.Camera
import com.twix.task_certification.model.CameraPreview
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationSideEffect
import com.twix.task_certification.model.TaskCertificationUiState
import com.twix.task_certification.model.TorchStatus
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

            TaskCertificationIntent.TakePicture -> {
                takePicture()
            }

            is TaskCertificationIntent.ToggleCamera -> {
                toggleCamera(intent.lifecycleOwner)
            }

            is TaskCertificationIntent.ToggleFlash -> {
                toggleTorch()
            }

            is TaskCertificationIntent.PickPicture -> {
                updateCapturedCImage(intent.uri)
            }

            is TaskCertificationIntent.RetakePicture -> {
                setupRetake(intent.lifecycleOwner)
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

    private fun bindCamera(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            camera.bind(lifecycleOwner, uiState.value.lens)
        }
    }

    private fun updateCapturedCImage(uri: Uri?) {
        uri?.let {
            reduce { updateCapturedImage(uri) }
            if (uiState.value.torch == TorchStatus.On) toggleTorch()
        } ?: run {
            viewModelScope.launch {
                emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
            }
        }
    }

    private fun toggleCamera(lifecycleOwner: LifecycleOwner) {
        reduce { toggleLens() }
        bindCamera(lifecycleOwner)
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
        camera.toggleTorch(uiState.value.torch)
    }

    private fun setupRetake(lifecycleOwner: LifecycleOwner) {
        reduce { removeCapture() }
        bindCamera(lifecycleOwner)
    }
}
