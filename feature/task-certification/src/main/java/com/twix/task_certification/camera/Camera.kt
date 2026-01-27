package com.twix.task_certification.camera

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.SurfaceRequest
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow

interface Camera {
    val surfaceRequests: StateFlow<SurfaceRequest?>

    suspend fun bind(
        lifecycleOwner: LifecycleOwner,
        lens: CameraSelector,
    )

    suspend fun unbind()

    fun takePicture(
        onComplete: (Uri?) -> Unit,
        onFailure: (ImageCaptureException) -> Unit,
    )
}
