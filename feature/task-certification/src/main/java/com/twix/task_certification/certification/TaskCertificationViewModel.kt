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
import com.twix.util.bus.GoalRefreshBus
import com.twix.util.bus.TaskCertificationDetailRefreshBus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskCertificationViewModel(
    private val photologRepository: PhotoLogRepository,
    private val detailRefreshBus: TaskCertificationDetailRefreshBus,
    private val goalRefreshBus: GoalRefreshBus,
    saveStateHandle: SavedStateHandle,
) : BaseViewModel<TaskCertificationUiState, TaskCertificationIntent, TaskCertificationSideEffect>(
        TaskCertificationUiState(),
    ) {
    private val argGoalId: Long =
        requireNotNull(saveStateHandle[NavRoutes.TaskCertificationRoute.ARG_GOAL_ID]) { GOAL_ID_NOT_FOUND }

    private val argFrom: String =
        requireNotNull(saveStateHandle[NavRoutes.TaskCertificationRoute.ARG_FROM]) { FROM_NOT_FOUND }

    private val argPhotologId: Long =
        requireNotNull(saveStateHandle[NavRoutes.TaskCertificationRoute.ARG_PHOTOLOG_ID]) { PHOTOLOG_ID_NOT_FOUND }

    private val argComment: String =
        requireNotNull(saveStateHandle[NavRoutes.TaskCertificationRoute.ARG_COMMENT]) { COMMENT_NOT_FOUND }

    init {
        if (argFrom == NavRoutes.TaskCertificationRoute.NAME_EDITOR && argComment.isNotEmpty()) {
            reduceComment(argComment)
        }
    }

    override suspend fun handleIntent(intent: TaskCertificationIntent) {
        when (intent) {
            is TaskCertificationIntent.TakePicture -> takePicture(intent.uri)
            is TaskCertificationIntent.PickPicture -> pickPicture(intent.uri)
            is TaskCertificationIntent.ToggleLens -> reduceLens()
            is TaskCertificationIntent.ToggleTorch -> reduceTorch()
            is TaskCertificationIntent.RetakePicture -> setupRetake()
            is TaskCertificationIntent.UpdateComment -> reduceComment(intent.value)
            is TaskCertificationIntent.CommentFocusChanged -> reduceCommentFocus(intent.isFocused)
            is TaskCertificationIntent.TryUpload -> checkUpload()
            is TaskCertificationIntent.Upload -> upload(intent.image)
        }
    }

    private fun takePicture(uri: Uri?) {
        uri?.let { reducePicture(it) } ?: viewModelScope.launch {
            showToast(R.string.task_certification_image_capture_fail, ToastType.ERROR)
        }
    }

    private fun pickPicture(uri: Uri?) {
        uri?.let { reducePicture(uri) }
    }

    private fun reducePicture(uri: Uri) {
        reduce { updatePicture(uri) }
        if (uiState.value.hasMaxCommentLength.not()) {
            reduceCommentFocus(true)
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

    private fun reduceComment(comment: String) {
        reduce { updateComment(comment) }
    }

    private fun reduceCommentFocus(isFocused: Boolean) {
        reduce { updateCommentFocus(isFocused) }
    }

    private fun checkUpload() {
        viewModelScope.launch {
            val capture = currentState.capture
            if (capture !is CaptureStatus.Captured) return@launch

            if (!currentState.comment.canUpload) {
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
                photologRepository.uploadPhotologImage(
                    goalId = argGoalId,
                    bytes = image,
                    contentType = "image/jpeg",
                )
            },
            onSuccess = { fileName ->
                when (argFrom) {
                    NavRoutes.TaskCertificationRoute.NAME_DETAIL,
                    NavRoutes.TaskCertificationRoute.NAME_HOME,
                    -> uploadPhotolog(fileName)

                    NavRoutes.TaskCertificationRoute.NAME_EDITOR -> modifyPhotolog(fileName)
                }
            },
            onError = {
                showToast(R.string.task_certification_upload_fail, ToastType.ERROR)
            },
        )
    }

    private fun uploadPhotolog(fileName: String) {
        launchResult(
            block = {
                photologRepository.uploadPhotolog(
                    PhotologParam(
                        goalId = argGoalId,
                        fileName = fileName,
                        comment = currentState.comment.value,
                        verificationDate = LocalDate.now(),
                    ),
                )
            },
            onSuccess = {
                when (argFrom) {
                    NavRoutes.TaskCertificationRoute.NAME_DETAIL -> detailRefreshBus.notifyChanged()
                    NavRoutes.TaskCertificationRoute.NAME_HOME -> goalRefreshBus.notifyGoalListChanged()
                }
                tryEmitSideEffect(TaskCertificationSideEffect.NavigateToBack)
            },
            onError = {
                showToast(R.string.task_certification_upload_fail, ToastType.ERROR)
            },
        )
    }

    private fun modifyPhotolog(fileName: String) {
        launchResult(
            block = {
                photologRepository.modifyPhotolog(
                    photologId = argPhotologId,
                    fileName = fileName,
                    comment = currentState.comment.value,
                )
            },
            onSuccess = {
                detailRefreshBus.notifyChanged()
                tryEmitSideEffect(TaskCertificationSideEffect.NavigateToDetail)
            },
            onError = {
                showToast(R.string.task_certification_modify_fail, ToastType.ERROR)
            },
        )
    }

    private fun showToast(
        message: Int,
        type: ToastType,
    ) {
        viewModelScope.launch {
            emitSideEffect(TaskCertificationSideEffect.ShowToast(message, type))
        }
    }

    companion object {
        private const val ERROR_DISPLAY_DURATION_MS = 1500L
        private const val GOAL_ID_NOT_FOUND = "Goal Id Argument Not Found"
        private const val FROM_NOT_FOUND = "From Argument Not Found"
        private const val PHOTOLOG_ID_NOT_FOUND = "Photolog Id Argument Not Found"
        private const val COMMENT_NOT_FOUND = "Comment Argument Not Found"
    }
}
