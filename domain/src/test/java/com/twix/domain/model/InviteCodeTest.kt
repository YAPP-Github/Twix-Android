package com.twix.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class InviteCodeTest {
    @Test
    fun `유효한 초대 코드는 정상적으로 생성된다`() {
        val inviteCode = InviteCode("AB12CD34")

        assertThat(inviteCode.value).isEqualTo("AB12CD34")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "ab12CD34",
            "AB12CD3!",
            "        ",
        ],
    )
    fun `규칙에 맞지 않는 초대 코드는 예외가 발생한다`(invalidCode: String) {
        assertThatThrownBy { InviteCode(invalidCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("InviteCode must be 8 characters of uppercase letters and digits")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "ABC123",
            "AB12CD345",
        ],
    )
    fun `8자리가 아닌 초대 코드는 예외가 발생한다`(invalidLengthCode: String) {
        assertThatThrownBy { InviteCode(invalidLengthCode) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("InviteCode must be 8 characters of uppercase letters and digits")
    }
}
