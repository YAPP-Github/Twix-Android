package com.twix.task_certification.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.twix.task_certification.R
import com.twix.task_certification.detail.component.BackgroundCard
import com.twix.task_certification.detail.component.ForegroundCard
import com.twix.task_certification.detail.component.ReactionBar
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
    navigateToBack: () -> Unit,
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
        onBack = navigateToBack,
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

        Spacer(Modifier.height(103.dp))

        Box(modifier.fillMaxWidth()) {
            BackgroundCard(
                uiModel =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> uiState.photoLogs.myPhotologs
                        BetweenUs.PARTNER -> uiState.photoLogs.partnerPhotologs
                    },
                buttonTitle =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> stringResource(R.string.task_certification_image_upload)
                        BetweenUs.PARTNER -> stringResource(R.string.task_certification_detail_partner_sting)
                    },
                rotation =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> 0f
                        BetweenUs.PARTNER -> -8f
                    },
                onClick =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> onClickUpload
                        BetweenUs.PARTNER -> onClickSting
                    },
            )

            ForegroundCard(
                uiModel =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> uiState.photoLogs.myPhotologs
                        BetweenUs.PARTNER -> uiState.photoLogs.partnerPhotologs
                    },
                currentShow = uiState.currentShow,
                rotation =
                    when (uiState.currentShow) {
                        BetweenUs.ME -> -8f
                        BetweenUs.PARTNER -> 0f
                    },
            )
        }

        ReactionSection(
            visible = uiState.canReaction,
            reaction = uiState.photoLogs.partnerPhotologs.reaction,
            onClickReaction = onClickReaction,
        )
    }
}

@Composable
private fun ReactionSection(
    visible: Boolean,
    reaction: GoalReactionType? = null,
    onClickReaction: (GoalReactionType) -> Unit,
) {
    if (visible) {
        Spacer(Modifier.height(85.dp))

        ReactionBar(
            selectedReaction = reaction,
            onSelectReaction = onClickReaction,
            modifier = Modifier.padding(horizontal = 20.dp),
        )
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
