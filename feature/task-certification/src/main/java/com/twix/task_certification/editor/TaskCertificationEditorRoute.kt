package com.twix.task_certification.editor

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.components.button.AppRoundButton
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.designsystem.extension.showCameraPermissionToastWithNavigateToSettingAction
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.task_certification.R
import com.twix.task_certification.detail.TaskCertificationDetailViewModel
import com.twix.task_certification.detail.component.CertificatedCard
import com.twix.task_certification.detail.component.PhotologCard
import com.twix.task_certification.detail.component.TaskCertificationDetailTopBar
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState
import com.twix.task_certification.detail.preview.TaskCertificationDetailPreviewProvider
import com.twix.ui.extension.findActivity
import com.twix.ui.extension.hasCameraPermission
import com.twix.ui.extension.noRippleClickable
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import com.twix.designsystem.R as DesR

@Composable
fun TaskCertificationEditorRoute(
    navigateToBack: () -> Unit,
    navigateToCertification: (Long) -> Unit,
    toastManager: ToastManager = koinInject(),
    viewModel: TaskCertificationDetailViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val currentContext by rememberUpdatedState(context)
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->

            if (granted) {
                navigateToCertification(uiState.currentGoalId)
                return@rememberLauncherForActivityResult
            }
            val activity = currentContext.findActivity() ?: return@rememberLauncherForActivityResult
            val shouldShowRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.CAMERA,
                )
            coroutineScope.launch {
                if (!shouldShowRationale) {
                    toastManager.showCameraPermissionToastWithNavigateToSettingAction(currentContext)
                } else {
                    toastManager.show(
                        ToastData(
                            currentContext.getString(
                                DesR.string.toast_camera_permission_request,
                            ),
                            ToastType.ERROR,
                        ),
                    )
                }
            }
        }

    TaskCertificationEditorScreen(
        uiState = uiState,
        onBack = navigateToBack,
        onClickSave = { },
        onClickRetake = {
            if (currentContext.hasCameraPermission()) {
                navigateToCertification(uiState.currentGoalId)
            } else {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        },
    )
}

@Composable
fun TaskCertificationEditorScreen(
    uiState: TaskCertificationDetailUiState,
    onBack: () -> Unit,
    onClickSave: () -> Unit,
    onClickRetake: (Long) -> Unit,
) {
    Scaffold(
        topBar = {
            TaskCertificationDetailTopBar(
                actionTitle = stringResource(DesR.string.word_save),
                goalTitle = uiState.currentGoal.goalName,
                onBack = onBack,
                onClickModify = onClickSave,
                modifier =
                    Modifier
                        .background(color = CommonColor.White),
            )
        },
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = CommonColor.White),
        ) {
            Spacer(Modifier.height(103.dp))

            PhotologCard {
                CertificatedCard(
                    imageUrl = uiState.displayedGoalImageUrl,
                    comment = uiState.displayedGoalComment,
                )
            }

            Spacer(Modifier.height(101.dp))

            AppRoundButton(
                text = stringResource(R.string.task_certification_editor_retake),
                textColor = GrayColor.C500,
                backgroundColor = CommonColor.White,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(68.dp)
                        .padding(horizontal = 30.dp)
                        .noRippleClickable { onClickRetake(uiState.currentGoalId) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCertificationEditorScreenPreview(
    @PreviewParameter(TaskCertificationDetailPreviewProvider::class)
    uiState: TaskCertificationDetailUiState,
) {
    TwixTheme {
        TaskCertificationEditorScreen(
            uiState = uiState,
            onBack = {},
            onClickSave = {},
            onClickRetake = {},
        )
    }
}
