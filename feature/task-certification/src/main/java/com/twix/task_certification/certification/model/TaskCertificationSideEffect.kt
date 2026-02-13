package com.twix.task_certification.certification.model

import com.twix.ui.base.SideEffect

sealed interface TaskCertificationSideEffect : SideEffect {
    data object ShowImageCaptureFailToast : TaskCertificationSideEffect
}
