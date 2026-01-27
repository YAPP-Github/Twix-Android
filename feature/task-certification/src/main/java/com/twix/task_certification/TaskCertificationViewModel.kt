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
                pickPicture(intent.uri)
            }

            is TaskCertificationIntent.RetakePicture -> {
                setupRetake(intent.lifecycleOwner)
            }
        }
    }

    private fun takePicture() {
        camera.takePicture(
            onComplete = { uri ->
                savePicture(uri, TaskCertificationSideEffect.ImageCaptureFailException)
                unbindCamera()
            },
            onFailure = {
                viewModelScope.launch {
                    emitSideEffect(TaskCertificationSideEffect.ImageCaptureFailException)
                }
            },
        )
    }

    private fun pickPicture(uri: Uri?) {
        savePicture(uri, TaskCertificationSideEffect.ImagePickFailException)
    }

    private fun savePicture(
        uri: Uri?,
        sideEffect: TaskCertificationSideEffect,
    ) {
        uri?.let {
            reduce { this.updatePicture(uri) }
            if (uiState.value.torch == TorchStatus.On) toggleTorch()
        } ?: run {
            viewModelScope.launch {
                emitSideEffect(sideEffect)
            }
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

    private fun toggleCamera(lifecycleOwner: LifecycleOwner) {
        reduce { toggleLens() }
        bindCamera(lifecycleOwner)
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
        camera.toggleTorch(uiState.value.torch)
    }

    private fun setupRetake(lifecycleOwner: LifecycleOwner) {
        reduce { removePicture() }
        bindCamera(lifecycleOwner)
    }
}
