package com.twix.datastore

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
internal data class AuthConfigure(
    val accessToken: String =
        "eyJhbGciOiJIUzM4NCJ9." +
            "eyJzdWIiOiIxIiwidHlwZSI6ImFjY2VzcyIsImlhdCI6MTc3MDI0NzM0NCwiZXhwIjoxNzcwODUyMTQ0fQ." +
            "67rDscm8BeayYFA1gfcEMliEdEh8-HTUyE5TwmAT8Ef8ZvtaWczxpMNZqI5htiek",
    val refreshToken: String =
        "eyJhbGciOiJIUzM4NCJ9." +
            "eyJzdWIiOiIxIiwidHlwZSI6InJlZnJlc2giLCJpYXQiOjE3NzAyNDczNDQsImV4cCI6MTc3MDg1MjE0NH0." +
            "zgUYdR6onyeY5EaH2_pWLs1rjNLf8m8ZeXsY7Cbk99a_2tzR0rDBZO_hdGTnorRL",
)

internal object AuthConfigureSerializer : Serializer<AuthConfigure> {
    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    override val defaultValue: AuthConfigure
        get() = AuthConfigure()

    override suspend fun readFrom(input: InputStream): AuthConfigure =
        try {
            json.decodeFromString(
                deserializer = AuthConfigure.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            defaultValue
        }

    override suspend fun writeTo(
        t: AuthConfigure,
        output: OutputStream,
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                json
                    .encodeToString(
                        serializer = AuthConfigure.serializer(),
                        value = t,
                    ).encodeToByteArray(),
            )
        }
    }
}
