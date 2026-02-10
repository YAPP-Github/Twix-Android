package com.twix.task_certification.certification

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.twix.designsystem.components.toast.model.ToastType
import com.twix.domain.model.photo.PhotologParam
import com.twix.domain.repository.PhotoLogRepository
import com.twix.navigation.NavRoutes
import com.twix.task_certification.R
import com.twix.task_certification.certification.model.CaptureStatus
import com.twix.task_certification.certification.model.TaskCertificationIntent
import com.twix.task_certification.certification.model.TaskCertificationSideEffect
import com.twix.task_certification.certification.model.TaskCertificationUiState
import com.twix.ui.base.BaseViewModel
import com.twix.util.bus.TaskCertificationRefreshBus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskCertificationViewModel(
    private val photologRepository: PhotoLogRepository,
    private val eventBus: TaskCertificationRefreshBus,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    private val goalId: Long =
        saveStateHandle[NavRoutes.TaskCertificationRoute.ARG_GOAL_ID]
            ?: throw IllegalStateException(GOAL_ID_NOT_FOUND)

    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.TakePicture -> takePicture(intent.uri)
            is TaskCertificationIntent.PickPicture -> pickPicture(intent.uri)
            is TaskCertificationIntent.ToggleLens -> reduceLens()
            is TaskCertificationIntent.ToggleTorch -> reduceTorch()
            is TaskCertificationIntent.RetakePicture -> setupRetake()
            is TaskCertificationIntent.UpdateComment -> updateComment(intent)
            is TaskCertificationIntent.CommentFocusChanged -> updateCommentFocus(intent.isFocused)
            is TaskCertificationIntent.TryUpload -> checkUpload()
            is TaskCertificationIntent.Upload -> upload(intent.image)
        }
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

    private fun checkUpload() {
        viewModelScope.launch {
            val capture = currentState.capture
            if (capture !is CaptureStatus.Captured) return@launch

            if (!currentState.commentUiModel.canUpload) {
                reduce { showCommentError() }
                delay(ERROR_DISPLAY_DURATION_MS)
                reduce { hideCommentError() }
                return@launch
            }

            emitSideEffect(
                TaskCertificationSideEffect.GetImageFromUri(capture.uri),
            )
        }
    }

    private fun upload(image: ByteArray) {
        launchResult(
            block = {
                photologRepository.uploadPhotoLogImage(
                    goalId = goalId,
                    bytes = image,
                    contentType = "image/jpeg",
                )
            },
            onSuccess = { fileName -> uploadPhotoLog(fileName) },
            onError = {
                TaskCertificationSideEffect.ShowToast(
                    R.string.task_certification_upload_fail,
                    ToastType.ERROR,
                )
            },
        )
    }

    private fun uploadPhotoLog(fileName: String) {
        launchResult(
            block = {
                photologRepository.uploadPhotoLog(
                    PhotologParam(
                        goalId = goalId,
                        fileName = fileName,
                        comment = currentState.commentUiModel.comment.text,
                        verificationDate = LocalDate.now(),
                    ),
                )
            },
            onSuccess = {
                eventBus.notifyChanged()
                tryEmitSideEffect(TaskCertificationSideEffect.NavigateToDetail)
            },
            onError = {
                TaskCertificationSideEffect.ShowToast(
                    R.string.task_certification_upload_fail,
                    ToastType.ERROR,
                )
            },
        )
    }

    companion object {
        private const val ERROR_DISPLAY_DURATION_MS = 1500L
        private const val GOAL_ID_NOT_FOUND = "Goal Id Argument Not Found"
    }
}
