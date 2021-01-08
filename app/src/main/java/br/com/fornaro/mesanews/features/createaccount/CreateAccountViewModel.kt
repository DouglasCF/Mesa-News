package br.com.fornaro.mesanews.features.createaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.handlers.ExceptionMapper
import br.com.fornaro.mesanews.domain.usecase.CreateAccountUseCase
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val createAccountUseCase: CreateAccountUseCase
) : ViewModel() {

    private val _state = MutableLiveData<CreateAccountState>()
    val state: LiveData<CreateAccountState> get() = _state

    fun signUp(name: String, email: String, password: String) {
        val handler = ExceptionMapper { error ->
            _state.value = CreateAccountState.Error(error)
        }

        viewModelScope.launch(handler) {
            _state.value = CreateAccountState.Loading
            createAccountUseCase.execute(name, email, password)
            _state.value = CreateAccountState.Success
        }
    }
}

sealed class CreateAccountState {
    object Success : CreateAccountState()
    object Loading : CreateAccountState()
    data class Error(val error: ErrorType) : CreateAccountState()
}