package com.peto.task_certification.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.peto.task_certification.R
import com.twix.designsystem.theme.GrayColor

@Composable
internal fun CameraControlBar(
    modifier: Modifier = Modifier,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp)
                .padding(bottom = 115.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(GrayColor.C300)
                    .border(
                        width = 2.dp,
                        color = GrayColor.C300,
                        shape = RoundedCornerShape(5.dp),
                    ),
        ) {
            Image(
                painter = painterResource(R.drawable.btn),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(3.dp)),
            )
        }

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_shutter),
            contentDescription = "카메라 촬영 버튼",
            modifier =
                Modifier.clickable {
                    onCaptureClick()
                },
        )
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_camera_toggle),
            contentDescription = "카메라 토글 버튼",
            modifier =
                Modifier.clickable {
                    onToggleCameraClick()
                },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraControlBarPreview() {
    CameraControlBar(
        onCaptureClick = {},
        onToggleCameraClick = {},
    )
}
