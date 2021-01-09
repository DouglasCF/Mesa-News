package br.com.fornaro.mesanews.features.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.usecase.SignInUseCase
import br.com.fornaro.mesanews.domain.usecase.ValidEmailUseCase
import br.com.fornaro.mesanews.domain.usecase.ValidPasswordUseCase
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
class LoginViewModelTest : BaseCoroutinesTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel

    @RelaxedMockK
    private lateinit var signInUseCase: SignInUseCase

    @RelaxedMockK
    private lateinit var validEmailUseCase: ValidEmailUseCase

    @RelaxedMockK
    private lateinit var validPasswordUseCase: ValidPasswordUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = LoginViewModel(
            signInUseCase = signInUseCase,
            validEmailUseCase = validEmailUseCase,
            validPasswordUseCase = validPasswordUseCase
        )
    }

    @Test
    fun `should sign in successfully`() {
        viewModel.signIn(anyString(), anyString())

        assertEquals(SignInState.Success, viewModel.state.value)
    }

    @Test
    fun `should not sign in when a error happens`() {
        coEvery { signInUseCase.execute(anyString(), anyString()) } throws Exception()

        viewModel.signIn(anyString(), anyString())

        assertEquals(SignInState.Error(ErrorType.GENERIC_ERROR), viewModel.state.value)
    }
}