package com.twix.task_certification

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.coroutineScope
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.task_certification.camera.Camera
import com.twix.task_certification.component.CameraControlBar
import com.twix.task_certification.component.CameraPreviewBox
import com.twix.task_certification.component.TaskCertificationTopBar
import com.twix.task_certification.model.CameraPreview
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationUiState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun TaskCertificationRoute(
    camera: Camera = koinInject(),
    viewModel: TaskCertificationViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPreview by camera.surfaceRequests.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val context = androidx.compose.ui.platform.LocalContext.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED,
        )
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->
            hasPermission = granted
        }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    DisposableEffect(lifecycleOwner, uiState.lens, hasPermission) {
        if (hasPermission) {
            coroutineScope.launch {
                camera.bind(lifecycleOwner, uiState.lens)
            }
        }

        onDispose {
            coroutineScope.launch {
                camera.unbind()
            }
        }
    }

    LaunchedEffect(uiState.torch, hasPermission) {
        if (hasPermission) {
            camera.toggleTorch(uiState.torch)
        }
    }

    TaskCertificationScreen(
        uiState = uiState,
        cameraPreview = cameraPreview,
        onClickClose = navigateToBack,
        onCaptureClick = {
            if (!hasPermission) return@TaskCertificationScreen

            coroutineScope.launch {
                camera
                    .takePicture()
                    .onSuccess {
                        viewModel.dispatch(TaskCertificationIntent.TakePicture(it))
                    }
            }
        },
        onToggleCameraClick = {
            viewModel.dispatch(TaskCertificationIntent.ToggleLens)
        },
        onClickFlash = {
            viewModel.dispatch(TaskCertificationIntent.ToggleTorch)
        },
    )
}

@Composable
private fun TaskCertificationScreen(
    uiState: TaskCertificationUiState,
    cameraPreview: CameraPreview?,
    onClickClose: () -> Unit,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
    onClickFlash: () -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(GrayColor.C500),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskCertificationTopBar(
            onClickClose = onClickClose,
        )

        Spacer(modifier = Modifier.height(24.26.dp))

        AppText(
            text = stringResource(R.string.task_certification_title),
            style = AppTextStyle.H2,
            color = GrayColor.C100,
        )

        Spacer(modifier = Modifier.height(40.dp))

        CameraPreviewBox(
            capture = uiState.capture,
            previewRequest = cameraPreview,
            torch = uiState.torch,
            onClickFlash = { onClickFlash() },
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
            cameraPreview = null,
            onClickClose = {},
            onCaptureClick = {},
            onToggleCameraClick = {},
            onClickFlash = {},
        )
    }
}
