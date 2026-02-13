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
            savedStateHandle.get<String>(NavRoutes.TaskCertificationEditorRoute.ARG_DATA)?.let { encoded ->
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
    }

    companion object {
        private const val SERIALIZER_NOT_FOUND = "Serializer Not Found"
    }
}
