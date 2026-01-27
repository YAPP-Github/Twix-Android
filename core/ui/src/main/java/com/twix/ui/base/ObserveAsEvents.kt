package com.twix.ui.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * [Flow]를 통해 전달되는 일회성 이벤트(Side Effect)를 Lifecycle에 안전하게 관찰하기 위한 Composable 함수입니다.
 * * 주로 ViewModel의 Channel이나 SharedFlow를 통해 전달되는 네비게이션, 스낵바 표시 등의
 * UI 이벤트를 처리할 때 사용됩니다.
 *
 * @param T 이벤트의 타입.
 * @param flow 관찰할 [Flow] 인스턴스 (예: ViewModel의 events).
 * @param onEvent 이벤트가 발생했을 때 실행할 suspend 콜백 함수.
 *
 */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    event: suspend (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(flow, lifecycleOwner) {
        // lifecycleOwner가 STARTED 상태일 때만 수집을 시작
        // STOPPED 상태가 되면 수집 코루틴을 자동으로 취소
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            // UI 변경 작업은 즉시 메인 스레드에서 실행되도록 보장
            withContext(Dispatchers.Main.immediate) {
                flow.collect(event)
            }
        }
    }
}
