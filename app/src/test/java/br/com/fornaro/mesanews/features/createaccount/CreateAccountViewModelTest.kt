package br.com.fornaro.mesanews.features.createaccount

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.usecase.*
import br.com.fornaro.mesanews.tools.BaseCoroutinesTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class CreateAccountViewModelTest : BaseCoroutinesTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CreateAccountViewModel

    @RelaxedMockK
    private lateinit var createAccountUseCase: CreateAccountUseCase

    @RelaxedMockK
    private lateinit var validNameUseCase: ValidNameUseCase

    @RelaxedMockK
    private lateinit var validEmailUseCase: ValidEmailUseCase

    @RelaxedMockK
    private lateinit var validPasswordUseCase: ValidPasswordUseCase

    @RelaxedMockK
    private lateinit var validConfirmPasswordUseCase: ValidConfirmPasswordUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = CreateAccountViewModel(
            createAccountUseCase = createAccountUseCase,
            validNameUseCase = validNameUseCase,
            validEmailUseCase = validEmailUseCase,
            validPasswordUseCase = validPasswordUseCase,
            validConfirmPasswordUseCase = validConfirmPasswordUseCase
        )
    }

    @Test
    fun `should sign in successfully`() {
        viewModel.signUp(anyString(), anyString(), anyString(), anyString())

        assertEquals(CreateAccountState.Success, viewModel.state.value)
    }

    @Test
    fun `should not sign in when a error happens`() {
        coEvery {
            createAccountUseCase.execute(
                anyString(),
                anyString(),
                anyString()
            )
        } throws Exception()

        viewModel.signUp(anyString(), anyString(), anyString(), anyString())

        assertEquals(CreateAccountState.Error(ErrorType.GENERIC_ERROR), viewModel.state.value)
    }
}