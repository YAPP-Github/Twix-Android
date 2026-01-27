package com.twix.task_certification.model

import androidx.lifecycle.LifecycleOwner
import com.twix.ui.base.Intent

sealed interface TaskCertificationIntent : Intent {
    data object TakePicture : TaskCertificationIntent

    data class BindCamera(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent

    data class ToggleCamera(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent

    data object ToggleFlash : TaskCertificationIntent
}
