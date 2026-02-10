package com.twix.task_certification.certification

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.components.comment.CIRCLE_PADDING_START
import com.twix.designsystem.components.comment.CIRCLE_SIZE
import com.twix.designsystem.components.comment.model.CommentUiModel
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.designsystem.theme.DimmedColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.task_certification.R
import com.twix.task_certification.certification.camera.Camera
import com.twix.task_certification.certification.component.CameraControlBar
import com.twix.task_certification.certification.component.CameraPreviewBox
import com.twix.task_certification.certification.component.CommentErrorText
import com.twix.task_certification.certification.component.TaskCertificationTopBar
import com.twix.task_certification.certification.model.CameraPreview
import com.twix.task_certification.certification.model.TaskCertificationIntent
import com.twix.task_certification.certification.model.TaskCertificationSideEffect
import com.twix.task_certification.certification.model.TaskCertificationUiState
import com.twix.ui.base.ObserveAsEvents
import com.twix.ui.extension.noRippleClickable
import com.twix.ui.extension.uriToByteArray
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import com.twix.designsystem.R as DesR

@Composable
fun TaskCertificationRoute(
    toastManager: ToastManager = koinInject(),
    camera: Camera = koinInject(),
    viewModel: TaskCertificationViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPreview by camera.surfaceRequests.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentContext by rememberUpdatedState(context)
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.dispatch(TaskCertificationIntent.PickPicture(uri))
        }

    DisposableEffect(lifecycleOwner, uiState.lens) {
        coroutineScope.launch {
            camera.bind(lifecycleOwner, uiState.lens)
        }

        onDispose {
            camera.unbind()
        }
    }

    LaunchedEffect(uiState.torch) {
        camera.toggleTorch(uiState.torch)
    }

    val imageCaptureFailMessage = stringResource(R.string.task_certification_image_capture_fail)
    ObserveAsEvents(viewModel.sideEffect) { event ->
        when (event) {
            TaskCertificationSideEffect.ShowImageCaptureFailToast -> {
                toastManager.tryShow(
                    ToastData(
                        message = imageCaptureFailMessage,
                        type = ToastType.ERROR,
                    ),
                )
            }

            is TaskCertificationSideEffect.ShowToast -> {
                toastManager.tryShow(
                    ToastData(
                        message = currentContext.getString(event.message),
                        type = event.type,
                    ),
                )
            }
            is TaskCertificationSideEffect.GetImageFromUri -> {
                val image = currentContext.uriToByteArray(event.uri)
                viewModel.dispatch(TaskCertificationIntent.Upload(image))
            }
            TaskCertificationSideEffect.NavigateToDetail -> navigateToBack()
        }
    }

    TaskCertificationScreen(
        uiState = uiState,
        cameraPreview = cameraPreview,
        onClickClose = navigateToBack,
        onCaptureClick = {
            coroutineScope.launch {
                camera
                    .takePicture()
                    .onSuccess {
                        viewModel.dispatch(TaskCertificationIntent.TakePicture(it))
                    }.onFailure {
                        viewModel.dispatch(TaskCertificationIntent.TakePicture(null))
                    }
            }
        },
        onToggleCameraClick = {
            viewModel.dispatch(TaskCertificationIntent.ToggleLens)
        },
        onClickFlash = {
            viewModel.dispatch(TaskCertificationIntent.ToggleTorch)
        },
        onClickGallery = {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        onClickRefresh = {
            viewModel.dispatch(TaskCertificationIntent.RetakePicture)
        },
        onCommentChanged = {
            viewModel.dispatch(TaskCertificationIntent.UpdateComment(it))
        },
        onFocusChanged = {
            viewModel.dispatch(TaskCertificationIntent.CommentFocusChanged(it))
        },
        onClickUpload = {
            viewModel.dispatch(TaskCertificationIntent.TryUpload)
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
    onClickGallery: () -> Unit,
    onClickRefresh: () -> Unit,
    onClickUpload: () -> Unit,
    onCommentChanged: (TextFieldValue) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    var textFieldRect by remember { mutableStateOf(Rect.Zero) }
    var guideTextRect by remember { mutableStateOf(Rect.Zero) }

    Column(
        Modifier
            .fillMaxSize()
            .background(GrayColor.C500)
            .noRippleClickable { focusManager.clearFocus() }
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TaskCertificationTopBar(
            onClickClose = onClickClose,
        )

        Spacer(modifier = Modifier.height(24.26.dp))

        AnimatedContent(targetState = uiState.showCommentError) { isError ->
            if (isError) {
                CommentErrorText()
            } else {
                AppText(
                    text = stringResource(R.string.task_certification_take_picture),
                    style = AppTextStyle.H2,
                    color = GrayColor.C100,
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        CameraPreviewBox(
            showTorch = uiState.showTorch,
            capture = uiState.capture,
            commentUiModel = uiState.commentUiModel,
            previewRequest = cameraPreview,
            torch = uiState.torch,
            onClickFlash = onClickFlash,
            onCommentChanged = onCommentChanged,
            onFocusChanged = onFocusChanged,
            onGuideTextPositioned = { guideTextRect = it },
            onTextFieldPositioned = { textFieldRect = it },
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

    DimmedScreen(
        isFocused = uiState.commentUiModel.isFocused,
        textFieldRect = textFieldRect,
        guideTextRect = guideTextRect,
        focusManager = focusManager,
    )
}

@Composable
fun DimmedScreen(
    isFocused: Boolean,
    textFieldRect: Rect,
    guideTextRect: Rect,
    focusManager: FocusManager,
) {
    if (isFocused && textFieldRect != Rect.Zero) {
        val density = LocalDensity.current
        val circleSizePx = with(density) { CIRCLE_SIZE.toPx() }
        val radiusPx = circleSizePx / 2
        val paddingStartPx = with(density) { CIRCLE_PADDING_START.toPx() }

        Canvas(
            modifier =
                Modifier
                    .fillMaxSize()
                    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                    .noRippleClickable { focusManager.clearFocus() },
        ) {
            drawRect(color = DimmedColor.D070)

            repeat(CommentUiModel.COMMENT_COUNT) { index ->
                val cx = textFieldRect.left + radiusPx + (index * paddingStartPx)
                val cy = textFieldRect.top + radiusPx

                drawCircle(
                    color = Color.Transparent,
                    radius = radiusPx,
                    center = Offset(cx, cy),
                    blendMode = BlendMode.Clear,
                )
            }
        }

        if (guideTextRect != Rect.Zero) {
            Box(
                modifier =
                    Modifier.offset {
                        IntOffset(guideTextRect.left.toInt(), guideTextRect.top.toInt())
                    },
            ) {
                AppText(
                    text = stringResource(DesR.string.comment_condition_guide),
                    style = AppTextStyle.B2,
                    color = GrayColor.C100,
                )
            }
        }
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
            onClickGallery = {},
            onClickRefresh = {},
            onClickUpload = {},
            onCommentChanged = {},
            onFocusChanged = {},
        )
    }
}
