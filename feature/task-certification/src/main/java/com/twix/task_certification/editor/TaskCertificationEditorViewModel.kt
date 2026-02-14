package com.twix.task_certification.editor

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.twix.domain.repository.PhotoLogRepository
import com.twix.navigation.NavRoutes
import com.twix.navigation.serializer.TaskCertificationSerializer
import com.twix.task_certification.editor.model.TaskCertificationEditorIntent
import com.twix.task_certification.editor.model.TaskCertificationEditorSideEffect
import com.twix.task_certification.editor.model.TaskCertificationEditorUiState
import com.twix.ui.base.BaseViewModel
import kotlinx.serialization.json.Json

class TaskCertificationEditorViewModel(
    private val photologRepository: PhotoLogRepository,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskCertificationEditorUiState, TaskCertificationEditorIntent, TaskCertificationEditorSideEffect>(
        TaskCertificationEditorUiState(),
    ) {
    val serializer =
        requireNotNull(
            savedStateHandle
                .get<String>(NavRoutes.TaskCertificationEditorRoute.ARG_DATA)
                ?.let { encoded ->
                    val json = Uri.decode(encoded)
                    Json.decodeFromString<TaskCertificationSerializer>(json)
                },
        ) { SERIALIZER_NOT_FOUND }

    init {
        reduceInitialState()
    }

    private fun reduceInitialState() {
        reduce { updateInitialState(serializer) }
    }

    override suspend fun handleIntent(intent: TaskCertificationEditorIntent) {
        when (intent) {
            is TaskCertificationEditorIntent.CommentFocusChanged -> reduceCommentFocus(intent.isFocused)
            is TaskCertificationEditorIntent.ModifyComment -> reduceComment(intent.value)
            TaskCertificationEditorIntent.Save -> TODO("인증샷 수정 API 연동")
        }
    }

    private fun reduceCommentFocus(isFocused: Boolean) {
        reduce { updateCommentFocus(isFocused) }
    }

    private fun reduceComment(comment: String) {
        reduce { updateComment(comment) }
    }

    companion object {
        private const val SERIALIZER_NOT_FOUND = "Serializer Not Found"
    }
}
