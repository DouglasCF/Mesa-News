package br.com.fornaro.mesanews.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidPasswordException
import br.com.fornaro.mesanews.domain.usecase.ValidPasswordUseCase
import org.junit.Before
import org.junit.Test

class ValidPasswordUseCaseTest {

    private lateinit var useCase: ValidPasswordUseCase

    @Before
    fun setup() {
        useCase = ValidPasswordUseCase()
    }

    @Test(expected = InvalidPasswordException::class)
    fun `should throw exception when password is less than minimum characters`() {
        useCase.execute("12345")
    }

    @Test
    fun `should not throw exception when password is valid`() {
        useCase.execute("123456")
    }
}