package com.twix.login

import com.twix.test.CoroutinesTestExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(CoroutinesTestExtension::class)
class ExampleTest {
    @Test
    fun `코루틴 테스트 예제`() =
        runTest {
            // Given
            val expected = "Hello, Coroutines!"

            // When
            val actual = suspendFunction()

            // Then
            assert(actual == expected)
        }

    private suspend fun suspendFunction(): String = "Hello, Coroutines!"
}
