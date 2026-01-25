package com.peto.task_certification.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CaptureCamera(
    private val context: Context,
) : Camera {
    private val _surfaceRequests = MutableStateFlow<SurfaceRequest?>(null)
    override val surfaceRequests: StateFlow<SurfaceRequest?> = _surfaceRequests.asStateFlow()

    private val imageCapture: ImageCapture =
        ImageCapture
            .Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
            .setJpegQuality(JPEG_QUALITY)
            .setOutputFormat(ImageCapture.OUTPUT_FORMAT_JPEG)
            .build()

    private val preview: Preview =
        Preview.Builder().build().apply {
            setSurfaceProvider { request ->
                _surfaceRequests.value = request
            }
        }

    override suspend fun bind(
        lifecycleOwner: androidx.lifecycle.LifecycleOwner,
        lens: CameraSelector,
    ) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        provider.unbindAll()
        provider.bindToLifecycle(
            lifecycleOwner,
            lens,
            preview,
            imageCapture,
        )
    }

    override fun takePicture(
        onComplete: (Uri?) -> Unit,
        onFailure: (ImageCaptureException) -> Unit,
    ) {
        val contentValues = contentValues()
        val outputOptions = outputFileOptions(contentValues)

        capture(
            imageCapture = imageCapture,
            outputOptions = outputOptions,
            onComplete = onComplete,
            onFailure = onFailure,
        )
    }

    private fun contentValues(): ContentValues =
        ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                IMAGE_NAME.format(System.currentTimeMillis()),
            )
            put(MediaStore.MediaColumns.MIME_TYPE, IMAGE_MIME_TYPE)
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, IMAGE_PATH)
            }
        }

    private fun outputFileOptions(contentValues: ContentValues): ImageCapture.OutputFileOptions =
        ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues,
            ).build()

    private fun capture(
        imageCapture: ImageCapture,
        outputOptions: ImageCapture.OutputFileOptions,
        onComplete: (Uri?) -> Unit,
        onFailure: (ImageCaptureException) -> Unit,
    ) {
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(result: ImageCapture.OutputFileResults) {
                    onComplete(result.savedUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    onFailure(exception)
                }
            },
        )
    }

    override suspend fun unbind() {
        ProcessCameraProvider.awaitInstance(context).unbindAll()
    }

    companion object Companion {
        private const val JPEG_QUALITY = 95

        private const val IMAGE_MIME_TYPE = "image/jpeg"
        private const val IMAGE_NAME = "task_%d"
        private const val IMAGE_PATH = "Pictures/TaskCertification"
    }
}
