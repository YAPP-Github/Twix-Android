package com.twix.task_certification.editor.model

import androidx.compose.runtime.Immutable
import com.twix.designsystem.components.comment.model.CommentUiModel
import com.twix.navigation.serializer.TaskCertificationSerializer
import com.twix.ui.base.State

@Immutable
data class TaskCertificationEditorUiState(
    val nickname: String = "",
    val goalName: String = "",
    val photologId: Long = -1,
    val imageUrl: String = "",
    val comment: CommentUiModel = CommentUiModel(),
    val originComment: String = "",
) : State {
    val isCommentNotChanged: Boolean
        get() = comment.value == originComment

    fun updateCommentFocus(isFocus: Boolean) = copy(comment = comment.updateFocus(isFocus))

    fun updateComment(value: String) = copy(comment = comment.updateComment(value))

    val imageName: String
        get() = imageUrl.split(IMAGE_NAME_SEPARATOR).last()

    fun updateInitialState(serializer: TaskCertificationSerializer) =
        copy(
            nickname = serializer.nickname,
            goalName = serializer.goalName,
            photologId = serializer.photologId,
            imageUrl = serializer.imageUrl,
            comment = CommentUiModel(serializer.comment.orEmpty()),
            originComment = serializer.comment.orEmpty(),
        )

    companion object {
        private const val IMAGE_NAME_SEPARATOR = "/"
    }
}
