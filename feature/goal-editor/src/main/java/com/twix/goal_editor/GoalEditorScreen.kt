package com.twix.goal_editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.R
import com.twix.designsystem.components.button.AppButton
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.RepeatType
import com.twix.goal_editor.component.EmojiPicker
import com.twix.goal_editor.component.GoalEditorTopBar
import com.twix.goal_editor.component.GoalInfoCard
import com.twix.goal_editor.component.GoalTextField
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.extension.dismissKeyboardOnTap
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun GoalEditorRoute(
    viewModel: GoalEditorViewModel = koinViewModel(),
    toastManager: ToastManager = koinInject(),
    isEdit: Boolean = false,
    navigateToBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is GoalEditorSideEffect.ShowToast -> toastManager.tryShow(ToastData(effect.message, effect.type))
            }
        }
    }

    GoalEditorScreen(
        uiState = uiState,
        isEdit = isEdit,
        onBack = navigateToBack,
        onCommitTitle = { viewModel.dispatch(GoalEditorIntent.UpdateTitle(it)) },
        onSelectedRepeatType = { viewModel.dispatch(GoalEditorIntent.UpdateRepeatType(it)) },
    )
}

@Composable
fun GoalEditorScreen(
    uiState: GoalEditorUiState,
    isEdit: Boolean = false,
    onBack: () -> Unit,
    onCommitTitle: (String) -> Unit,
    onSelectedRepeatType: (RepeatType) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(CommonColor.White)
                .dismissKeyboardOnTap(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoalEditorTopBar(
            isEdit = isEdit,
            onBack = onBack,
        )

        Spacer(Modifier.height(52.dp))

        EmojiPicker(
            emojiId = uiState.selectedIconId,
            onClick = {},
        )

        Spacer(Modifier.height(44.dp))

        GoalTextField(
            value = uiState.goalTitle,
            onCommitTitle = onCommitTitle,
        )

        Spacer(Modifier.height(44.dp))

        GoalInfoCard(
            selectedRepeatType = uiState.selectedRepeatType,
            repeatCount = uiState.repeatCount,
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            onSelectedRepeatType = onSelectedRepeatType,
            onShowRepeatCountBottomSheet = {},
            onShowCalendarBottomSheet = {},
        )

        Spacer(Modifier.weight(1f))

        AppButton(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
            text = stringResource(R.string.word_completion),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TwixTheme {
        val uiState = GoalEditorUiState()

        GoalEditorScreen(
            uiState = uiState,
            onBack = {},
            onCommitTitle = {},
            onSelectedRepeatType = {},
        )
    }
}
