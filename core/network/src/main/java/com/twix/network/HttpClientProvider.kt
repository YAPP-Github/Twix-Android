package com.twix.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal object HttpClientProvider {
    fun createHttpClient(
        baseUrl: String,
        isDebug: Boolean,
    ): HttpClient =
        HttpClient {
            expectSuccess = true

            configureContentNegotiation(isDebug)
            configureLogging(isDebug)
            configureTimeout()
            configureDefaultRequest(baseUrl)

            // TODO : 토큰 관련 기능 구현 후 적용
//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        BearerTokens(
//                            accessToken = "",
//                            refreshToken = "",
//                        )
//                    }
//              }
//            }
        }

    private fun HttpClientConfig<*>.configureContentNegotiation(isDebug: Boolean) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = isDebug
                },
            )
        }
    }

    private fun HttpClientConfig<*>.configureLogging(isDebug: Boolean) {
        install(Logging) {
            level =
                if (isDebug) {
                    LogLevel.BODY
                } else {
                    LogLevel.NONE
                }
            logger = Logger.ANDROID

            sanitizeHeader { header -> header == SANITIZE_HEADER }
        }
    }

    private fun HttpClientConfig<*>.configureTimeout() {
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT_MILLIS
            requestTimeoutMillis = TIMEOUT_MILLIS
            socketTimeoutMillis = TIMEOUT_MILLIS
        }
    }

    private fun HttpClientConfig<*>.configureDefaultRequest(baseUrl: String) {
        defaultRequest {
            url(baseUrl)
            contentType(ContentType.Application.Json)
        }
    }

    private const val SANITIZE_HEADER = "Authorization"
    private const val TIMEOUT_MILLIS = 30_000L
}
