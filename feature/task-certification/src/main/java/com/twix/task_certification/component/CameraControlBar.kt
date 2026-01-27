package com.twix.task_certification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.twix.designsystem.theme.TwixTheme
import com.twix.task_certification.R
import com.twix.task_certification.model.CaptureStatus
import com.twix.ui.extension.noRippleClickable

@Composable
internal fun CameraControlBar(
    capture: CaptureStatus,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
    onClickGallery: () -> Unit,
    onClickRefresh: () -> Unit,
    onClickUpload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (capture) {
        CaptureStatus.NotCaptured -> {
            ImageNotCapturedBar(
                onCaptureClick = onCaptureClick,
                onToggleCameraClick = onToggleCameraClick,
                onClickGallery = onClickGallery,
                modifier = modifier,
            )
        }

        is CaptureStatus.Captured -> {
            ImageCapturedBar(
                onClickRefresh = onClickRefresh,
                onClickUpload = onClickUpload,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun ImageNotCapturedBar(
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
    onClickGallery: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 41.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.btn),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .noRippleClickable(onClickGallery),
        )

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_shutter),
            contentDescription = null,
            modifier =
                Modifier
                    .noRippleClickable(onCaptureClick),
        )
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_toggle),
            contentDescription = null,
            modifier =
                Modifier
                    .noRippleClickable(onToggleCameraClick),
        )
    }
}

@Composable
private fun ImageCapturedBar(
    onClickRefresh: () -> Unit,
    onClickUpload: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .padding(horizontal = 58.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_refresh),
            contentDescription = null,
            modifier =
                Modifier
                    .size(50.dp)
                    .noRippleClickable(onClickRefresh),
        )

        Spacer(modifier = modifier.width(12.dp))

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_upload),
            contentDescription = null,
            modifier =
                Modifier
                    .noRippleClickable(onClickUpload),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun CameraControlBarPreview() {
    TwixTheme {
        Column {
            CameraControlBar(
                capture = CaptureStatus.NotCaptured,
                onCaptureClick = {},
                onToggleCameraClick = {},
                onClickGallery = {},
                onClickRefresh = {},
                onClickUpload = {},
            )

            CameraControlBar(
                capture = CaptureStatus.Captured("".toUri()),
                onCaptureClick = {},
                onToggleCameraClick = {},
                onClickGallery = {},
                onClickRefresh = {},
                onClickUpload = {},
            )
        }
    }
}
