package com.twix.goal_editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.R
import com.twix.designsystem.components.bottomsheet.CommonBottomSheet
import com.twix.designsystem.components.bottomsheet.model.CommonBottomSheetConfig
import com.twix.designsystem.components.button.AppButton
import com.twix.designsystem.components.calendar.Calendar
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.enums.RepeatType
import com.twix.goal_editor.component.EmojiPicker
import com.twix.goal_editor.component.GoalEditorTopBar
import com.twix.goal_editor.component.GoalInfoCard
import com.twix.goal_editor.component.GoalTextField
import com.twix.goal_editor.component.label
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.extension.dismissKeyboardOnTap
import com.twix.ui.extension.noRippleClickable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.time.LocalDate

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
        onCommitTitle = { viewModel.dispatch(GoalEditorIntent.SetTitle(it)) },
        onSelectRepeatType = { viewModel.dispatch(GoalEditorIntent.SetRepeatType(it)) },
        onCommitEndDate = { viewModel.dispatch(GoalEditorIntent.SetEndDate(it)) },
        onCommitStartDate = { viewModel.dispatch(GoalEditorIntent.SetStartDate(it)) },
        onCommitRepeatCount = { viewModel.dispatch(GoalEditorIntent.SetRepeatCount(it)) },
        onToggleEndDateEnabled = { viewModel.dispatch(GoalEditorIntent.SetEndDateEnabled(it)) },
        onComplete = { viewModel.dispatch(GoalEditorIntent.Save) },
    )
}

@Composable
fun GoalEditorScreen(
    uiState: GoalEditorUiState,
    isEdit: Boolean = false,
    onBack: () -> Unit,
    onCommitTitle: (String) -> Unit,
    onSelectRepeatType: (RepeatType) -> Unit,
    onCommitEndDate: (LocalDate) -> Unit,
    onCommitStartDate: (LocalDate) -> Unit,
    onCommitRepeatCount: (Int) -> Unit,
    onToggleEndDateEnabled: (Boolean) -> Unit,
    onComplete: () -> Unit,
) {
    var showRepeatCountBottomSheet by remember { mutableStateOf(false) }
    var showCalendarBottomSheet by remember { mutableStateOf(false) }
    var isEndDate by remember { mutableStateOf(true) }

    Box {
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
                endDateEnabled = uiState.endDateEnabled,
                endDate = uiState.endDate,
                onSelectedRepeatType = onSelectRepeatType,
                onShowRepeatCountBottomSheet = { showRepeatCountBottomSheet = true },
                onShowCalendarBottomSheet = {
                    isEndDate = it
                    showCalendarBottomSheet = true
                },
                onToggleEndDateEnabled = onToggleEndDateEnabled,
            )

            Spacer(Modifier.weight(1f))

            AppButton(
                onClick = onComplete,
                modifier =
                    Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                text = stringResource(R.string.word_completion),
            )
        }

        CommonBottomSheet(
            visible = showCalendarBottomSheet,
            config = CommonBottomSheetConfig(showHandle = false),
            onDismissRequest = { showCalendarBottomSheet = false },
            content = {
                Calendar(
                    initialDate = if (isEndDate) uiState.endDate else uiState.startDate,
                    onComplete = {
                        if (isEndDate) onCommitEndDate(it) else onCommitStartDate(it)
                        showCalendarBottomSheet = false
                    },
                )
            },
        )

        CommonBottomSheet(
            visible = showRepeatCountBottomSheet,
            config = CommonBottomSheetConfig(showHandle = false),
            onDismissRequest = { showRepeatCountBottomSheet = false },
            content = {
                RepeatCountBottomSheetContent(
                    repeatCount = uiState.repeatCount,
                    selectedRepeatType = uiState.selectedRepeatType,
                    onCommit = { repeatType, repeatCount ->
                        onSelectRepeatType(repeatType)
                        onCommitRepeatCount(repeatCount)
                        showRepeatCountBottomSheet = false
                    },
                )
            },
        )
    }
}

@Composable
private fun RepeatCountBottomSheetContent(
    repeatCount: Int,
    selectedRepeatType: RepeatType,
    onCommit: (RepeatType, Int) -> Unit,
) {
    var internalRepeatCount by remember { mutableIntStateOf(repeatCount) }
    var internalSelectedRepeatType by remember { mutableStateOf(selectedRepeatType) }
    val maxCount = if (internalSelectedRepeatType == RepeatType.WEEKLY) 6 else 25

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AppText(
                text = RepeatType.WEEKLY.label(),
                style = AppTextStyle.B2,
                color = if (internalSelectedRepeatType == RepeatType.WEEKLY) CommonColor.White else GrayColor.C500,
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (internalSelectedRepeatType == RepeatType.WEEKLY) GrayColor.C500 else CommonColor.White)
                        .border(1.dp, GrayColor.C500, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 5.5.dp)
                        .noRippleClickable(onClick = {
                            internalSelectedRepeatType = RepeatType.WEEKLY
                            internalRepeatCount = 0
                        }),
            )

            AppText(
                text = RepeatType.MONTHLY.label(),
                style = AppTextStyle.B2,
                color = if (internalSelectedRepeatType == RepeatType.MONTHLY) CommonColor.White else GrayColor.C500,
                modifier =
                    Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (internalSelectedRepeatType == RepeatType.MONTHLY) GrayColor.C500 else CommonColor.White)
                        .border(1.dp, GrayColor.C500, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 5.5.dp)
                        .noRippleClickable(onClick = {
                            internalSelectedRepeatType = RepeatType.MONTHLY
                            internalRepeatCount = 0
                        }),
            )
        }

        Spacer(Modifier.height(36.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_minus),
                contentDescription = "minus",
                colorFilter = ColorFilter.tint(CommonColor.White),
                modifier =
                    Modifier
                        .background(GrayColor.C500, CircleShape)
                        .padding(4.dp)
                        .size(28.dp)
                        .noRippleClickable(onClick = { if (internalRepeatCount > 1) internalRepeatCount-- }),
            )

            Row(
                modifier =
                    Modifier
                        .width(96.dp)
                        .border(1.dp, GrayColor.C300, RoundedCornerShape(12.dp))
                        .padding(horizontal = 27.5.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                AppText(
                    text = internalRepeatCount.toString(),
                    style = AppTextStyle.H2,
                    color = GrayColor.C500,
                )

                Spacer(Modifier.width(8.dp))

                Box(
                    modifier =
                        Modifier
                            .size(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    AppText(
                        text = stringResource(R.string.word_count),
                        style = AppTextStyle.T2,
                        color = GrayColor.C300,
                    )
                }
            }

            Image(
                painter = painterResource(R.drawable.ic_plus),
                contentDescription = "plus",
                colorFilter = ColorFilter.tint(CommonColor.White),
                modifier =
                    Modifier
                        .background(GrayColor.C500, CircleShape)
                        .padding(4.dp)
                        .size(28.dp)
                        .noRippleClickable(onClick = { if (maxCount > internalRepeatCount) internalRepeatCount++ }),
            )
        }

        Spacer(Modifier.height(32.dp))

        AppButton(
            onClick = { onCommit(internalSelectedRepeatType, internalRepeatCount) },
            modifier =
                Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
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
            onSelectRepeatType = {},
            onCommitEndDate = {},
            onCommitStartDate = {},
            onCommitRepeatCount = {},
            onToggleEndDateEnabled = {},
            onComplete = {},
            isEdit = false,
        )
    }
}
