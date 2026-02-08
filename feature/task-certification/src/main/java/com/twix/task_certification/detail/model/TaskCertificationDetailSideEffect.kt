package com.twix.task_certification.detail.model

import com.twix.designsystem.components.toast.model.ToastType
import com.twix.ui.base.SideEffect

sealed interface TaskCertificationDetailSideEffect : SideEffect {
    data class ShowToast(
        val message: Int,
        val type: ToastType,
    ) : TaskCertificationDetailSideEffect

    data class NavigateToUpload(
        val goalId: Long,
    ) : TaskCertificationDetailSideEffect
}
