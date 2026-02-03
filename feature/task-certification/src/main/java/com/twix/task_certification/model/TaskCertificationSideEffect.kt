package com.twix.task_certification.model

import com.twix.ui.base.SideEffect

sealed interface TaskCertificationSideEffect : SideEffect {
    data object ShowImageCaptureFailToast : TaskCertificationSideEffect
}
