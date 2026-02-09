package com.twix.task_certification.certification

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.twix.task_certification.certification.model.TaskCertificationIntent
import com.twix.task_certification.certification.model.TaskCertificationSideEffect
import com.twix.task_certification.certification.model.TaskCertificationUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TaskCertificationViewModel :
    BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.InitGoal -> reduceGoalId(intent.goalId)
            is TaskCertificationIntent.TakePicture -> takePicture(intent.uri)
            is TaskCertificationIntent.PickPicture -> pickPicture(intent.uri)
            is TaskCertificationIntent.ToggleLens -> reduceLens()
            is TaskCertificationIntent.ToggleTorch -> reduceTorch()
            is TaskCertificationIntent.RetakePicture -> setupRetake()
            is TaskCertificationIntent.UpdateComment -> updateComment(intent)
            is TaskCertificationIntent.CommentFocusChanged -> updateCommentFocus(intent.isFocused)
            is TaskCertificationIntent.Upload -> upload()
        }
    }

    private fun reduceGoalId(goalId: Long) {
        reduce { copy(goalId = goalId) }
    }

    private fun takePicture(uri: Uri?) {
        uri?.let { reducePicture(it) } ?: viewModelScope.launch {
            emitSideEffect(
                TaskCertificationSideEffect.ShowImageCaptureFailToast,
            )
        }
    }

    private fun pickPicture(uri: Uri?) {
        uri?.let { reducePicture(uri) }
    }

    private fun reducePicture(uri: Uri) {
        reduce { updatePicture(uri) }
        if (uiState.value.hasMaxCommentLength.not()) {
            updateCommentFocus(true)
        }
    }

    private fun reduceLens() {
        reduce { toggleLens() }
    }

    private fun reduceTorch() {
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

    private fun upload() {
        if (uiState.value.commentUiModel.canUpload
                .not()
        ) {
            viewModelScope.launch {
                reduce { showCommentError() }
                delay(ERROR_DISPLAY_DURATION_MS)
                reduce { hideCommentError() }
            }
        }
    }

    companion object {
        private const val ERROR_DISPLAY_DURATION_MS = 1500L
    }
}
