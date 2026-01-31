package com.twix.task_certification

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.twix.task_certification.model.TaskCertificationIntent
import com.twix.task_certification.model.TaskCertificationSideEffect
import com.twix.task_certification.model.TaskCertificationUiState
import com.twix.task_certification.model.TorchStatus
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TaskCertificationViewModel :
    BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.TakePicture -> {
                takePicture(intent.uri)
            }

            is TaskCertificationIntent.PickPicture -> {
                pickPicture(intent.uri)
            }

            is TaskCertificationIntent.ToggleLens -> {
                toggleLens()
            }

            is TaskCertificationIntent.ToggleFlash -> {
                toggleTorch()
            }

            is TaskCertificationIntent.RetakePicture -> {
                setupRetake()
            }

            is TaskCertificationIntent.UpdateComment -> {
                updateComment(intent)
            }

            is TaskCertificationIntent.CommentFocusChanged -> {
                updateCommentFocus(intent.isFocused)
            }
        }
    }

    private fun takePicture(uri: Uri?) {
        uri?.let { updatePicture(it) } ?: viewModelScope.launch {
            emitSideEffect(
                TaskCertificationSideEffect.ImageCaptureFailException,
            )
        }
    }

    private fun pickPicture(uri: Uri?) {
        uri?.let { updatePicture(uri) }
    }

    private fun updatePicture(uri: Uri) {
        reduce { updateCapturedImage(uri) }
        if (uiState.value.torch == TorchStatus.On) {
            reduce { toggleTorch() }
        }
    }

    private fun toggleLens() {
        reduce { toggleLens() }
    }

    private fun toggleTorch() {
        reduce { toggleTorch() }
    }

    private fun setupRetake() {
        reduce { removePicture() }
    }

    private fun updateComment(intent: TaskCertificationIntent.UpdateComment) {
        reduce { updateComment(intent.comment) }
    }

    private fun updateCommentFocus(isFocused: Boolean) {
        reduce { updateCommentFocus(isFocused) }
    }
}
