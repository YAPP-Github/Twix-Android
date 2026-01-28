package com.twix.goal_editor.model

import androidx.compose.runtime.Immutable
import com.twix.domain.model.enums.RepeatType
import com.twix.ui.base.State
import java.time.LocalDate

@Immutable
data class GoalEditorUiState(
    val selectedIconId: Long = -1L,
    val goalTitle: String = "",
    val selectedRepeatType: RepeatType = RepeatType.DAILY,
    val repeatCount: Int = 0,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null,
) : State {
    val isEnabled: Boolean
        get() = selectedIconId != -1L && goalTitle.isNotBlank() && repeatCount > 0
}
