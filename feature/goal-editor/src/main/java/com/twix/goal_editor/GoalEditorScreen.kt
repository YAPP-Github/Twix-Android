package com.twix.goal_editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.R
import com.twix.designsystem.components.button.AppButton
import com.twix.designsystem.components.common.CommonSwitch
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.components.text_field.UnderlineTextField
import com.twix.designsystem.components.toast.ToastManager
import com.twix.designsystem.components.toast.model.ToastData
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.designsystem.theme.TwixTheme
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.enums.RepeatType
import com.twix.goal_editor.component.EmojiPicker
import com.twix.goal_editor.component.GoalEditorTopBar
import com.twix.goal_editor.model.GoalEditorUiState
import com.twix.ui.extension.dismissKeyboardOnTap
import com.twix.ui.extension.noRippleClickable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject
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
            when(effect) {
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

        GoalInfoSection(
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

@Composable
private fun GoalTextField(
    value: String,
    onCommitTitle: (String) -> Unit,
) {
    var internalValue by rememberSaveable(value) { mutableStateOf(value) }
    // 초기에 무의미하게 commit 되는 것을 방지하는 상태 변수
    var wasFocused by remember { mutableStateOf(false) }

    UnderlineTextField(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (wasFocused && !state.isFocused) {
                        onCommitTitle(internalValue.trim())
                    }
                    wasFocused = state.isFocused
                },
        value = internalValue,
        placeHolder = stringResource(R.string.goal_editor_text_field_placeholder),
        onValueChange = { internalValue = it },
    )
}

@Composable
private fun GoalInfoSection(
    selectedRepeatType: RepeatType,
    repeatCount: Int,
    startDate: LocalDate,
    endDate: LocalDate?,
    onSelectedRepeatType: (RepeatType) -> Unit,
    onShowRepeatCountBottomSheet: () -> Unit,
    onShowCalendarBottomSheet: (Boolean) -> Unit, // true면 endDate
) {
    var endDateVisible by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .border(1.dp, GrayColor.C500, RoundedCornerShape(12.dp)),
    ) {
        RepeatTypeSection(
            selectedRepeatType = selectedRepeatType,
            repeatCount = repeatCount,
            onSelectedRepeatType = onSelectedRepeatType,
            onShowRepeatCountBottomSheet = onShowRepeatCountBottomSheet,
        )

        HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)

        DateSection(
            date = startDate,
            onShowCalendarBottomSheet = { onShowCalendarBottomSheet(false) },
        )

        HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)

        EndDateSwitchSection(
            visible = endDateVisible,
            onToggle = { endDateVisible = it },
        )

        AnimatedVisibility(
            visible = endDateVisible,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
        ) {
            Column {
                HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)

                DateSection(
                    date = endDate ?: LocalDate.now(),
                    isEndDate = true,
                    onShowCalendarBottomSheet = { onShowCalendarBottomSheet(true) },
                )
            }
        }
    }
}

@Composable
private fun RepeatTypeSection(
    selectedRepeatType: RepeatType,
    repeatCount: Int,
    onSelectedRepeatType: (RepeatType) -> Unit,
    onShowRepeatCountBottomSheet: () -> Unit,
) {
    val animationDuration = 160

    Column(
        modifier =
            Modifier
                .padding(16.dp),
    ) {
        HeaderText(stringResource(R.string.header_repeat_type))

        Spacer(Modifier.height(12.dp))

        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RepeatType.entries.forEachIndexed { index, type ->
                val isSelected = selectedRepeatType == type

                AppText(
                    text = type.label(),
                    style = AppTextStyle.B2,
                    color = if (isSelected) CommonColor.White else GrayColor.C500,
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) GrayColor.C500 else CommonColor.White)
                            .border(1.dp, GrayColor.C500, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 5.5.dp)
                            .noRippleClickable(onClick = { onSelectedRepeatType(type) }),
                )

                if (index != RepeatType.entries.lastIndex) Spacer(Modifier.width(8.dp))
            }

            Spacer(Modifier.weight(1f))

            AnimatedVisibility(
                visible = selectedRepeatType != RepeatType.DAILY,
                enter = fadeIn(animationSpec = tween(durationMillis = animationDuration)),
                exit = fadeOut(animationSpec = tween(durationMillis = animationDuration)),
            ) {
                Row(
                    modifier =
                        Modifier
                            .noRippleClickable(onClick = onShowRepeatCountBottomSheet),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AppText(
                        text = "%s %s번".format(selectedRepeatType.label(), repeatCount),
                        style = AppTextStyle.B2,
                        color = GrayColor.C500,
                    )

                    Image(
                        painter = painterResource(R.drawable.ic_chevron_down_circle),
                        contentDescription = "repeat type",
                        modifier =
                            Modifier
                                .size(24.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun DateSection(
    date: LocalDate,
    isEndDate: Boolean = false,
    onShowCalendarBottomSheet: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HeaderText(stringResource(if (isEndDate) R.string.header_end_date else R.string.header_start_date))

        Spacer(Modifier.weight(1f))

        Row(
            modifier =
                Modifier
                    .noRippleClickable(onClick = onShowCalendarBottomSheet),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppText(
                text = "%s월 %s일".format(date.monthValue, date.dayOfMonth),
                style = AppTextStyle.B2,
                color = GrayColor.C500,
            )

            Image(
                painter = painterResource(R.drawable.ic_chevron_down_circle),
                contentDescription = "date",
                modifier =
                    Modifier
                        .size(24.dp),
            )
        }
    }
}

@Composable
private fun EndDateSwitchSection(
    visible: Boolean = false,
    onToggle: (Boolean) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HeaderText(stringResource(R.string.header_end_date_option))

        Spacer(Modifier.weight(1f))

        CommonSwitch(
            checked = visible,
            onClick = onToggle,
        )
    }
}

@Composable
private fun HeaderText(text: String) {
    AppText(
        text = text,
        style = AppTextStyle.B1,
        color = GrayColor.C500,
    )
}

@Composable
private fun RepeatType.label(): String =
    when (this) {
        RepeatType.DAILY -> stringResource(R.string.word_daily)
        RepeatType.WEEKLY -> stringResource(R.string.word_weekly)
        RepeatType.MONTHLY -> stringResource(R.string.word_monthly)
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
