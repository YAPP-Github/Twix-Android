package com.twix.task_certification.model

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.Immutable
import com.twix.ui.base.State

@Immutable
data class TaskCertificationUiState(
    val capture: CaptureStatus = CaptureStatus.NotCaptured,
    val torch: TorchStatus = TorchStatus.Off,
    val lens: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    val preview: CameraPreview? = null,
) : State {
    fun toggleLens(): TaskCertificationUiState {
        val newLens =
            if (lens == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
        return copy(lens = newLens)
    }

    fun toggleTorch(): TaskCertificationUiState {
        val newFlashMode = TorchStatus.toggle(torch)
        return copy(torch = newFlashMode)
    }

    fun updateCapturedImage(uri: Uri) = copy(capture = CaptureStatus.Captured(uri))

    fun removeCapture(): TaskCertificationUiState = copy(capture = CaptureStatus.NotCaptured)
}
