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

    /**
     * 플레이스홀더 표시 여부.
     *
     * 표시 조건
     * - 포커스 중이 아니거나
     * - 텍스트가 하나라도 존재하지 않으면
     *
     */
    val hidePlaceholder: Boolean
        get() = isFocused || comment.text.isNotEmpty()

    companion object {
        const val COMMENT_COUNT = 5
    }
}
