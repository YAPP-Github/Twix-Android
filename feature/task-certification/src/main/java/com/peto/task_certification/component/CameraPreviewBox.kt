package com.peto.task_certification.component

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.peto.task_certification.model.CameraPreview
import com.peto.task_certification.model.CaptureStatus
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme

@Composable
internal fun CameraPreviewBox(
    captureStatus: CaptureStatus,
    previewRequest: CameraPreview?,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .aspectRatio(1f)
                .border(
                    color = GrayColor.C400,
                    width = 2.dp,
                    shape = RoundedCornerShape(73.83.dp),
                ).clip(RoundedCornerShape(73.83.dp)),
    ) {
        when (captureStatus) {
            CaptureStatus.NotCaptured -> {
                previewRequest?.let {
                    CameraXViewfinder(
                        surfaceRequest = it.request,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            is CaptureStatus.Captured -> {
                AsyncImage(
                    model = captureStatus.uri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Preview
@Composable
private fun CameraPreviewBoxNotCapturedPreview() {
    TwixTheme {
        CameraPreviewBox(
            captureStatus = CaptureStatus.NotCaptured,
            previewRequest = null,
        )
    }
}
