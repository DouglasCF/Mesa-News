package br.com.fornaro.mesanews.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidConfirmPasswordException
import br.com.fornaro.mesanews.domain.usecase.ValidConfirmPasswordUseCase
import org.junit.Before
import org.junit.Test

class ValidConfirmPasswordUseCaseTest {

    private lateinit var useCase: ValidConfirmPasswordUseCase

    @Before
    fun setup() {
        useCase = ValidConfirmPasswordUseCase()
    }

    @Test(expected = InvalidConfirmPasswordException::class)
    fun `should throw exception when confirm password is not the same`() {
        useCase.execute("password", "confirm")
    }

    @Test
    fun `should not throw exception when confirm password is the same`() {
        useCase.execute("password", "password")
    }
}