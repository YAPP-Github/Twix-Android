package com.twix.designsystem.components.comment.model

import androidx.compose.runtime.Immutable

@Immutable
data class CommentUiModel(
    val comment: String = "",
    val isFocused: Boolean = false,
) {
    val hasMaxCommentLength: Boolean
        get() = comment.length == COMMENT_COUNT

    val canUpload: Boolean
        get() =
            comment.isEmpty() ||
                comment.isNotEmpty() &&
                hasMaxCommentLength

    fun updateComment(newComment: String): CommentUiModel = copy(comment = newComment)

    fun updateFocus(isFocused: Boolean) = copy(isFocused = isFocused)

    companion object {
        const val COMMENT_COUNT = 5
    }
}
