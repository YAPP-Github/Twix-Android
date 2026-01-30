package com.twix.task_certification.model

import android.net.Uri
import com.twix.ui.base.Intent

sealed interface TaskCertificationIntent : Intent {
    data class TakePicture(
        val uri: Uri?,
    ) : TaskCertificationIntent

    data object ToggleLens : TaskCertificationIntent

    data object ToggleFlash : TaskCertificationIntent
}
