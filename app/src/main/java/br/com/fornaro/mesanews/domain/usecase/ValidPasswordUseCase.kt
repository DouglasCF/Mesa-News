package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidPasswordException

class ValidPasswordUseCase {

    fun execute(password: String) {
        if (password.length < 6) throw InvalidPasswordException()
    }
}