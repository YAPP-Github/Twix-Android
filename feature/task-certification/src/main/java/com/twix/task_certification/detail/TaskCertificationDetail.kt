package com.twix.task_certification.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.BetweenUs
import com.twix.domain.model.enums.GoalReactionType
import com.twix.task_certification.detail.component.TaskCertificationDetailTopBar
import com.twix.task_certification.detail.model.TaskCertificationDetailIntent
import com.twix.task_certification.detail.model.TaskCertificationDetailSideEffect
import com.twix.task_certification.detail.model.TaskCertificationDetailUiState
import com.twix.task_certification.detail.preview.TaskCertificationDetailPreviewProvider
import com.twix.ui.base.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun TaskCertificationDetailRoute(
    goalId: Long,
    navigateToUpload: (Long) -> Unit,
    toastManager: ToastManager = koinInject(),
    viewModel: TaskCertificationDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentContext by rememberUpdatedState(context)

    LaunchedEffect(goalId) {
        if (goalId != -1L) {
            viewModel.dispatch(TaskCertificationDetailIntent.InitGoal(goalId))
        }
    }

    ObserveAsEvents(viewModel.sideEffect) { sideEffect ->
        when (sideEffect) {
            is TaskCertificationDetailSideEffect.ShowToast -> {
                toastManager.tryShow(
                    ToastData(currentContext.getString(sideEffect.message), sideEffect.type),
                )
            }

            is TaskCertificationDetailSideEffect.NavigateToUpload -> {
                navigateToUpload(sideEffect.goalId)
            }
        }
    }

    TaskCertificationDetailScreen(
        uiState = uiState,
        onBack = { },
        onClickModify = { },
        onClickReaction = { viewModel.dispatch(TaskCertificationDetailIntent.Reaction(it)) },
        onClickUpload = { viewModel.dispatch(TaskCertificationDetailIntent.Upload) },
        onClickSting = { viewModel.dispatch(TaskCertificationDetailIntent.Sting) },
    )
}

@Composable
fun TaskCertificationDetailScreen(
    uiState: TaskCertificationDetailUiState,
    onBack: () -> Unit,
    onClickModify: () -> Unit,
    onClickReaction: (GoalReactionType) -> Unit,
    onClickUpload: () -> Unit,
    onClickSting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxSize()
            .background(color = CommonColor.White),
    ) {
        TaskCertificationDetailTopBar(
            showModify = uiState.canModify,
            goalTitle = uiState.goalTitle,
            onBack = onBack,
            onClickModify = onClickModify,
        )

        Spacer(Modifier.height(113.dp))

        when (uiState.currentShow) {
            BetweenUs.ME ->
                MyTaskCertificationContent(
                    uiModel = uiState.photoLogs.myPhotologs,
                    onClickUpload = onClickUpload,
                )

            BetweenUs.PARTNER ->
                PartnerTaskCertificationContent(
                    uiModel = uiState.photoLogs.partnerPhotologs,
                    onClickReaction = onClickReaction,
                    onClickSting = onClickSting,
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCertificationDetailScreenPreview(
    @PreviewParameter(TaskCertificationDetailPreviewProvider::class)
    uiState: TaskCertificationDetailUiState,
) {
    TwixTheme {
        TaskCertificationDetailScreen(
            uiState = uiState,
            onBack = {},
            onClickModify = {},
            onClickReaction = {},
            onClickUpload = {},
            onClickSting = {},
        )
    }
}
