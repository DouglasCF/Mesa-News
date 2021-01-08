package br.com.fornaro.mesanews.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidNameException
import br.com.fornaro.mesanews.domain.usecase.ValidNameUseCase
import br.com.fornaro.mesanews.tools.assertDoesNotThrow
import org.junit.Before
import org.junit.Test

class ValidNameUseCaseTest {

    private lateinit var useCase: ValidNameUseCase

    @Before
    fun setup() {
        useCase = ValidNameUseCase()
    }

    @Test(expected = InvalidNameException::class)
    fun `should throw exception when name is blank`() {
        useCase.execute(" ")
    }

    @Test(expected = InvalidNameException::class)
    fun `should throw exception when name is empty`() {
        useCase.execute("")
    }

    @Test
    fun `should not throw exception when name is valid`() {
        assertDoesNotThrow { useCase.execute("name") }
    }
}