package com.peto.task_certification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peto.task_certification.component.CameraControlBar
import com.peto.task_certification.component.CameraPreviewBox
import com.peto.task_certification.component.TaskCertificationTopBar
import com.peto.task_certification.model.TaskCertificationIntent
import com.peto.task_certification.model.TaskCertificationUiState
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskCertificationRoute(
    viewModel: TaskCertificationViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val uiSate by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.dispatch(TaskCertificationIntent.BindCamera(lifecycleOwner))
    }

    TaskCertificationScreen(
        uiState = uiSate,
        onClickClose = {
            navigateToBack()
        },
        onCaptureClick = {
            viewModel.dispatch(TaskCertificationIntent.TakePicture)
        },
        onToggleCameraClick = {},
    )
}

@Composable
private fun TaskCertificationScreen(
    uiState: TaskCertificationUiState,
    onClickClose: () -> Unit,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(GrayColor.C500),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskCertificationTopBar(
            onClickClose = { onClickClose() },
        )

        Spacer(modifier = Modifier.height(24.26.dp))

        AppText(
            text = stringResource(R.string.task_certification_title),
            style = AppTextStyle.H2,
            color = GrayColor.C100,
        )

        Spacer(modifier = Modifier.height(40.dp))

        CameraPreviewBox(
            captureStatus = uiState.capture,
            previewRequest = uiState.preview,
        )

        Spacer(modifier = Modifier.height(52.dp))

        CameraControlBar(
            onCaptureClick = onCaptureClick,
            onToggleCameraClick = onToggleCameraClick,
        )
    }
}

@Preview
@Composable
fun TaskCertificationScreenPreview() {
    TwixTheme {
        TaskCertificationScreen(
            uiState = TaskCertificationUiState(),
            onClickClose = {},
            onCaptureClick = {},
            onToggleCameraClick = {},
        )
    }
}
