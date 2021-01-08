package br.com.fornaro.mesanews.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidEmailException
import br.com.fornaro.mesanews.domain.usecase.ValidEmailUseCase
import org.junit.Before
import org.junit.Test

class ValidEmailUseCaseTest {

    private lateinit var useCase: ValidEmailUseCase


    @Before
    fun setup() {
        useCase = ValidEmailUseCase()
    }

    @Test(expected = InvalidEmailException::class)
    fun `should throw exception when email is invalid`() {
        useCase.execute("email")
    }

    @Test
    fun `should not throw exception when email is valid`() {
        useCase.execute("valid@email.com")
    }
}