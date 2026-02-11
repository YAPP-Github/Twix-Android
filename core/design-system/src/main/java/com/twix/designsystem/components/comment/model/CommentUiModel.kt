package com.twix.designsystem.components.comment.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue

@Immutable
data class CommentUiModel(
    val comment: TextFieldValue = TextFieldValue(""),
    val isFocused: Boolean = false,
) {
    constructor(value: String) : this(TextFieldValue(value))

    val hasMaxCommentLength: Boolean
        get() = comment.text.length == COMMENT_COUNT

    val canUpload: Boolean
        get() =
            comment.text.isEmpty() ||
                comment.text.isNotEmpty() &&
                hasMaxCommentLength

    fun updateComment(newComment: String): CommentUiModel {
        if (newComment.length > COMMENT_COUNT) return this
        return copy(comment = TextFieldValue(newComment))
    }

    fun updateFocus(isFocused: Boolean) = copy(isFocused = isFocused)

    companion object {
        const val COMMENT_COUNT = 5
    }
}
