package com.twix.task_certification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.twix.task_certification.R
import com.twix.ui.extension.noRippleClickable

@Composable
internal fun CameraControlBar(
    modifier: Modifier = Modifier,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
    onClickGallery: () -> Unit,
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

@Preview(showBackground = true)
@Composable
private fun CameraControlBarPreview() {
    CameraControlBar(
        onCaptureClick = {},
        onToggleCameraClick = {},
        onClickGallery = {},
    )
}
