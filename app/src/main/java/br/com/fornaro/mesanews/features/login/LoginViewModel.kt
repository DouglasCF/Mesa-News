package br.com.fornaro.mesanews.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.exceptions.ExceptionMapper
import br.com.fornaro.mesanews.domain.usecase.SignInUseCase
import br.com.fornaro.mesanews.domain.usecase.ValidEmailUseCase
import br.com.fornaro.mesanews.domain.usecase.ValidPasswordUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val signInUseCase: SignInUseCase,
    private val validEmailUseCase: ValidEmailUseCase,
    private val validPasswordUseCase: ValidPasswordUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SignInState>()
    val state: LiveData<SignInState> get() = _state

    fun signIn(email: String, password: String) {
        val handler = ExceptionMapper { error ->
            _state.value = SignInState.Error(error)
        }

        viewModelScope.launch(handler) {
            _state.value = SignInState.Loading
            validEmailUseCase.execute(email)
            validPasswordUseCase.execute(password)
            signInUseCase.execute(email, password)
            _state.value = SignInState.Success
        }
    }
}

sealed class SignInState {
    object Success : SignInState()
    object Loading : SignInState()
    data class Error(val error: ErrorType) : SignInState()
}