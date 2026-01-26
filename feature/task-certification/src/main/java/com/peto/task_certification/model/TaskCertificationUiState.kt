package com.peto.task_certification.model

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.Immutable
import com.twix.ui.base.State

@Immutable
data class TaskCertificationUiState(
    val capture: CaptureStatus = CaptureStatus.NotCaptured,
    val lens: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    val preview: CameraPreview? = null,
) : State
