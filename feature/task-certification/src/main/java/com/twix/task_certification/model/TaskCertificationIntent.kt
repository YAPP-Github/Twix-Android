package com.twix.task_certification.model

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.twix.ui.base.Intent

sealed interface TaskCertificationIntent : Intent {
    data object TakePicture : TaskCertificationIntent

    data class PickPicture(
        val uri: Uri?,
    ) : TaskCertificationIntent

    data class BindCamera(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent

    data class ToggleCamera(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent

    data class ToggleFlash(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent

    data class RetakePicture(
        val lifecycleOwner: LifecycleOwner,
    ) : TaskCertificationIntent
}
