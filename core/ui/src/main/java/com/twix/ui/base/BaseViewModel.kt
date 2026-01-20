package com.twix.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel<S : State, I : Intent, SE : SideEffect>(
    initialState: S,
) : ViewModel() {
    protected open val logger: Logger =
        Logger.withTag(this::class.simpleName ?: "BaseViewModel")

    // State
    private val stateHolder = StateHolder(initialState)
    val state: StateFlow<S> = stateHolder.state
    protected val currentState: S get() = stateHolder.current

    // SideEffect
    private val sideEffectHolder = SideEffectHolder<SE>()
    val sideEffect: Flow<SE> = sideEffectHolder.flow

    // Intent
    private val intentChannel = Channel<I>(Channel.BUFFERED)

    init {
        // Intent 순차 처리
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                try {
                    handleIntent(intent)
                } catch (t: Throwable) {
                    if (t is CancellationException) throw t
                    handleError(t)
                }
            }
        }
    }

    /**
     * UI에서 Intent를 발생시키는 메서드
     * */
    fun dispatch(intent: I) {
        val result = intentChannel.trySend(intent)
        if (result.isFailure) {
            logger.w { "이벤트 유실: $intent, 원인 = ${result.exceptionOrNull()}" }
        }
    }

    /**
     * State를 변경하는 메서드
     * */
    protected fun reduce(reducer: S.() -> S) {
        stateHolder.reduce(reducer)
    }

    /**
     * SideEffect를 발생시키는 메서드
     * */
    protected suspend fun emitSideEffect(effect: SE) {
        sideEffectHolder.emit(effect)
    }

    /**
     * Intent를 처리하는 메서드
     * */
    protected abstract suspend fun handleIntent(intent: I)

    /**
     * 서버 통신 메서드 호출 및 응답을 처리하는 헬퍼 메서드
     * */
    protected fun <D> launchResult(
        onStart: (() -> Unit)? = null, // 비동기 시작 전 처리해야 할 로직 ex) 로딩
        onFinally: (() -> Unit)? = null, // 비동기 종료 후 리소스 정리
        onSuccess: (D) -> Unit, // 비동기 메서드 호출이 성공했을 때 처리해야 할 로직
        onError: ((Throwable) -> Unit)? = null, // 비동기 메서드 호출에 실패했을 때 처리해야 할 로직
        block: suspend () -> Result<D>, // 비동기 메서드 ex) 서버 통신 메서드
    ): Job =
        viewModelScope.launch {
            try {
                onStart?.invoke()

                val result = block.invoke()
                result.fold(
                    onSuccess = { data -> onSuccess(data) },
                    onFailure = { t ->
                        if (t is CancellationException) throw t

                        handleError(t)
                        onError?.invoke(t)
                    },
                )
            } finally {
                onFinally?.invoke()
            }
        }

    /**
     * 에러 핸들링 메서드
     * */
    protected open fun handleError(t: Throwable) {
        logger.e { "에러 발생: ${t.stackTraceToString()}" }
    }

    // 리소스 정리
    override fun onCleared() {
        intentChannel.close()
        super.onCleared()
    }
}
