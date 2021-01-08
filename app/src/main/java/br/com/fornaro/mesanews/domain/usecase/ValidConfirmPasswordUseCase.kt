package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidConfirmPasswordException

class ValidConfirmPasswordUseCase {

    fun execute(password: String, confirmPassword: String) {
        if (password != confirmPassword) throw InvalidConfirmPasswordException()
    }
}