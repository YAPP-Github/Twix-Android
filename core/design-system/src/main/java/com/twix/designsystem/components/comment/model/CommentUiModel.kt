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

    fun updateComment(newComment: TextFieldValue): CommentUiModel {
        if (comment.text.length > COMMENT_COUNT) return this
        return copy(comment = newComment)
    }

    fun updateFocus(isFocused: Boolean) = copy(isFocused = isFocused)

    /**
     * 특정 index 위치에 커서를 표시할지 여부를 반환한다.
     *
     * 표시 조건
     * 1. 포커스 상태일 것
     * 2. 현재 selection 시작 위치가 해당 index 일 것
     * 3. 해당 위치에 문자가 없을 것 (빈 칸)
     *
     * @param index 확인할 문자 위치
     */
    fun showCursor(index: Int): Boolean {
        val isCharEmpty = comment.text.getOrNull(index) == null
        return isFocused && comment.selection.start == index && isCharEmpty
    }

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
