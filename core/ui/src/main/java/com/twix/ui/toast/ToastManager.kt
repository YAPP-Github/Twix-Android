package com.twix.ui.toast

import com.twix.ui.toast.model.ToastData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ToastManager {
    private val _toasts =
        MutableSharedFlow<ToastData>(
            replay = 0,
            extraBufferCapacity = 32,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )
    val toasts: SharedFlow<ToastData> = _toasts

    suspend fun show(data: ToastData) {
        _toasts.emit(data)
    }
}
