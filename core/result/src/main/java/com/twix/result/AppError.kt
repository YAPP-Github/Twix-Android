package com.twix.result

import java.io.IOException

sealed interface AppError {
    /** HTTP 4xx/5xx */
    data class Http(
        val status: Int, // HTTP status (e.code())
        val code: String? = null, // 서버에서 반환하는 커스텀 코드 ex) G5000
        val message: String? = null, // 서버에서 반환하는 message
        val rawBody: String? = null, // Http 에러 원문
    ) : AppError

    /** 네트워크 끊김/불가 */
    data class Network(
        val cause: IOException? = null,
    ) : AppError

    /** 타임아웃 */
    data class Timeout(
        val cause: Throwable? = null,
    ) : AppError

    /** 파싱/직렬화 */
    data class Serialization(
        val cause: Throwable? = null,
    ) : AppError

    /** 그 외 */
    data class Unknown(
        val cause: Throwable,
    ) : AppError
}
