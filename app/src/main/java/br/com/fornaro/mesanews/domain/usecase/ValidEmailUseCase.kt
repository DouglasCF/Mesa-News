package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidEmailException

class ValidEmailUseCase {

    fun execute(email: String) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            throw InvalidEmailException()
    }
}