package com.twix.task_certification.model

import android.net.Uri
import androidx.compose.ui.text.input.TextFieldValue
import com.twix.ui.base.Intent

sealed interface TaskCertificationIntent : Intent {
    data class TakePicture(
        val uri: Uri?,
    ) : TaskCertificationIntent

    data class PickPicture(
        val uri: Uri?,
    ) : TaskCertificationIntent

    data object ToggleLens : TaskCertificationIntent

    data object ToggleTorch : TaskCertificationIntent

    data object RetakePicture : TaskCertificationIntent

    data class UpdateComment(
        val comment: TextFieldValue,
    ) : TaskCertificationIntent

    data class CommentFocusChanged(
        val isFocused: Boolean,
    ) : TaskCertificationIntent

    data object Upload : TaskCertificationIntent
}
