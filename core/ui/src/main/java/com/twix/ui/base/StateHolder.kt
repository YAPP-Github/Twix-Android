package com.twix.ui.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class StateHolder<S : State>(
    initialState: S,
) {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    val current: S
        get() = _state.value

    fun reduce(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }
}
