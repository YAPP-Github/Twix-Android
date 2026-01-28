package com.twix.goal_editor

import com.twix.designsystem.components.toast.model.ToastType
import com.twix.ui.base.SideEffect

interface GoalEditorSideEffect : SideEffect {
    data class ShowToast(val message: String, val type: ToastType) : GoalEditorSideEffect
}
