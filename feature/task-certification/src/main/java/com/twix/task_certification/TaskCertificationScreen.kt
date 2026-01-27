package com.twix.task_certification

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.task_certification.component.CameraControlBar
import com.twix.task_certification.component.CameraPreviewBox
import com.twix.task_certification.component.TaskCertificationTopBar
import com.twix.task_certification.model.CaptureStatus
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationSideEffect
import com.twix.task_certification.model.TaskCertificationUiState
import com.twix.ui.base.ObserveAsEvents
import com.twix.ui.toast.ToastManager
import com.twix.ui.toast.model.ToastData
import com.twix.ui.toast.model.ToastType
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun TaskCertificationRoute(
    toastManager: ToastManager = koinInject(),
    viewModel: TaskCertificationViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            TaskCertificationSideEffect.ImageCaptureFailException -> {
                toastManager.tryShow(
                    ToastData(
                        message = context.getString(R.string.task_certification_image_capture_fail),
                        type = ToastType.ERROR,
                    ),
                )
            }

            TaskCertificationSideEffect.ImagePickFailException -> {
                toastManager.tryShow(
                    ToastData(
                        message = context.getString(R.string.task_certification_image_pick_fail),
                        type = ToastType.ERROR,
                    ),
                )
            }
        }
    }

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.dispatch(TaskCertificationIntent.PickPicture(uri))
        }

    LaunchedEffect(Unit) {
        viewModel.dispatch(TaskCertificationIntent.BindCamera(lifecycleOwner))
    }

    TaskCertificationScreen(
        uiState = uiState,
        onClickClose = {
            navigateToBack()
        },
        onCaptureClick = {
            viewModel.dispatch(TaskCertificationIntent.TakePicture)
        },
        onToggleCameraClick = {
            viewModel.dispatch(TaskCertificationIntent.ToggleCamera(lifecycleOwner))
        },
        onClickFlash = {
            viewModel.dispatch(TaskCertificationIntent.ToggleFlash(lifecycleOwner))
        },
        onClickGallery = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onClickUpload = {
        },
        onClickRefresh = {
            viewModel.dispatch(TaskCertificationIntent.RetakePicture(lifecycleOwner))
        },
    )
}

@Composable
private fun TaskCertificationScreen(
    uiState: TaskCertificationUiState,
    onClickClose: () -> Unit,
    onCaptureClick: () -> Unit,
    onToggleCameraClick: () -> Unit,
    onClickFlash: () -> Unit,
    onClickGallery: () -> Unit,
    onClickUpload: () -> Unit,
    onClickRefresh: () -> Unit,
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
            capture = uiState.capture,
            previewRequest = uiState.preview,
            torch = uiState.torch,
            onClickFlash = { onClickFlash() },
        )

        Spacer(modifier = Modifier.height(52.dp))

        CameraControlBar(
            capture = uiState.capture,
            onCaptureClick = onCaptureClick,
            onToggleCameraClick = onToggleCameraClick,
            onClickGallery = onClickGallery,
            onClickRefresh = onClickRefresh,
            onClickUpload = onClickUpload,
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
            onClickFlash = {},
            onClickGallery = {},
            onClickUpload = {},
            onClickRefresh = {},
        )
    }
}

@Preview
@Composable
fun CapturedTaskCertificationScreenPreview() {
    TwixTheme {
        TaskCertificationScreen(
            uiState = TaskCertificationUiState(capture = CaptureStatus.Captured("".toUri())),
            onClickClose = {},
            onCaptureClick = {},
            onToggleCameraClick = {},
            onClickFlash = {},
            onClickGallery = {},
            onClickUpload = {},
            onClickRefresh = {},
        )
    }
}
