package com.twix.goal_manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twix.designsystem.R
import com.twix.designsystem.components.calendar.WeeklyCalendar
import com.twix.designsystem.components.goal.GoalCardFrame
import com.twix.designsystem.components.popup.CommonPopup
import com.twix.designsystem.components.popup.CommonPopupDivider
import com.twix.designsystem.components.popup.CommonPopupItem
import com.twix.designsystem.components.text.AppText
import com.twix.designsystem.components.topbar.CommonTopBar
import com.twix.designsystem.extension.label
import com.twix.designsystem.theme.CommonColor
import com.twix.designsystem.theme.GrayColor
import com.twix.domain.model.enums.AppTextStyle
import com.twix.domain.model.goal.GoalSummary
import com.twix.goal_manage.model.GoalManageUiState
import com.twix.ui.extension.noRippleClickable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun GoalManageRoute(
    selectedDate: LocalDate,
    viewModel: GoalManageViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    navigateToGoalEditor: (Long) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(selectedDate) {
        viewModel.dispatch(GoalManageIntent.SetSelectedDate(selectedDate))
    }

    GoalManageScreen(
        uiState = uiState,
        onBack = popBackStack,
        onSelectDate = { viewModel.dispatch(GoalManageIntent.SetSelectedDate(it)) },
        onPreviousWeek = { viewModel.dispatch(GoalManageIntent.PreviousWeek) },
        onNextWeek = { viewModel.dispatch(GoalManageIntent.NextWeek) },
        onEdit = navigateToGoalEditor,
        onDelete = { viewModel.dispatch(GoalManageIntent.DeleteGoal(it)) },
        onEnd = { viewModel.dispatch(GoalManageIntent.EndGoal(it)) },
    )
}

@Composable
private fun GoalManageScreen(
    uiState: GoalManageUiState,
    onBack: () -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    onEdit: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onEnd: (Long) -> Unit = {},
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(CommonColor.White),
    ) {
        CommonTopBar(
            title = stringResource(R.string.word_edit),
            left = {
                Image(
                    painter = painterResource(R.drawable.ic_arrow3_left),
                    contentDescription = "back",
                    modifier =
                        Modifier
                            .padding(18.dp)
                            .size(24.dp)
                            .noRippleClickable(onClick = onBack),
                )
            },
        )

        Spacer(Modifier.height(4.dp))

        WeeklyCalendar(
            selectedDate = uiState.selectedDate,
            referenceDate = uiState.referenceDate,
            onSelectDate = onSelectDate,
            onPreviousWeek = onPreviousWeek,
            onNextWeek = onNextWeek,
        )

        GoalSummaryList(
            modifier =
                Modifier
                    .padding(horizontal = 20.dp)
                    .weight(1f),
            summaryList = uiState.goalSummaries,
            onEdit = onEdit,
            onDelete = onDelete,
            onEnd = onEnd,
        )
    }
}

@Composable
private fun GoalSummaryList(
    modifier: Modifier = Modifier,
    summaryList: List<GoalSummary>,
    onEdit: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onEnd: (Long) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        items(summaryList, key = { it.goalId }) { item ->
            GoalSummaryItem(
                item = item,
                onEdit = onEdit,
                onDelete = onDelete,
                onEnd = onEnd,
            )
        }
    }
}

@Composable
private fun GoalSummaryItem(
    item: GoalSummary,
    onEdit: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onEnd: (Long) -> Unit = {},
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var anchorOffset by remember { mutableStateOf(IntOffset(x = -180, y = 68)) }

    GoalCardFrame(
        goalName = item.name,
        goalIcon = item.icon,
        right = {
            Box(
                modifier = Modifier.wrapContentSize(),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_meatball),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .size(24.dp)
                            .noRippleClickable(onClick = { menuExpanded = true }),
                )

                CommonPopup(
                    visible = menuExpanded,
                    anchorOffset = anchorOffset,
                    onDismiss = { menuExpanded = false },
                ) {
                    Column(
                        modifier =
                            Modifier
                                .width(88.dp)
                                .background(CommonColor.White, RoundedCornerShape(12.dp))
                                .border(1.dp, GrayColor.C500, RoundedCornerShape(12.dp)),
                    ) {
                        CommonPopupItem(
                            text = stringResource(R.string.action_edit),
                            onClick = {
                                onEdit(item.goalId)
                                menuExpanded = false
                            },
                        )
                        CommonPopupDivider()
                        CommonPopupItem(
                            text = stringResource(R.string.action_finish),
                            onClick = {
                                onEnd(item.goalId)
                                menuExpanded = false
                            },
                        )
                        CommonPopupDivider()
                        CommonPopupItem(
                            text = stringResource(R.string.action_delete),
                            onClick = {
                                onDelete(item.goalId)
                                menuExpanded = false
                            },
                        )
                    }
                }
            }
        },
        content = {
            HorizontalDivider(thickness = 1.dp, color = GrayColor.C500)
            Column(
                modifier =
                    Modifier
                        .background(GrayColor.C050)
                        .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                val endDate =
                    item.endDate?.let { stringResource(R.string.date_year_month_day, it.year, it.monthValue, it.dayOfMonth) }
                        ?: stringResource(R.string.word_not_set)

                GoalSummaryInfo(
                    label = stringResource(R.string.header_repeat_type),
                    value = item.repeatCycle.label(),
                )
                GoalSummaryInfo(
                    label = stringResource(R.string.word_start_date),
                    value =
                        stringResource(
                            R.string.date_year_month_day,
                            item.startDate.year,
                            item.startDate.monthValue,
                            item.startDate.dayOfMonth,
                        ),
                )
                GoalSummaryInfo(
                    label = stringResource(R.string.word_end_date),
                    value = endDate,
                )
            }
        },
    )
}

@Composable
private fun GoalSummaryInfo(
    label: String,
    value: String,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(28.dp),
    ) {
        AppText(
            text = label,
            style = AppTextStyle.C1,
            color = GrayColor.C400,
            modifier = Modifier.width(48.dp),
            textAlign = TextAlign.Start,
        )

        AppText(
            text = value,
            style = AppTextStyle.B4,
            color = GrayColor.C500,
            modifier = Modifier.weight(1f),
        )
    }
}
