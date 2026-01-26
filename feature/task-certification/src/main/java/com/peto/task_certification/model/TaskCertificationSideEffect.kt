package com.peto.task_certification.model

import com.twix.ui.base.SideEffect

sealed interface TaskCertificationSideEffect : SideEffect {
    data object ImageCaptureFailException : TaskCertificationSideEffect
}
