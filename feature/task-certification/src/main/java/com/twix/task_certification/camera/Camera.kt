package com.twix.task_certification.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.LifecycleOwner
import com.twix.task_certification.model.CameraPreview
import com.twix.task_certification.model.TorchStatus
import kotlinx.coroutines.flow.StateFlow

interface Camera {
    val surfaceRequests: StateFlow<CameraPreview?>

    suspend fun bind(
        lifecycleOwner: LifecycleOwner,
        lens: CameraSelector,
    )

    suspend fun unbind()

    fun takePicture(
        onComplete: (Uri?) -> Unit,
        onFailure: (ImageCaptureException) -> Unit,
    )

    fun toggleTorch(torch: TorchStatus)
}
