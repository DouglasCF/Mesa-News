package br.com.fornaro.mesanews.domain.usecase

import androidx.core.util.PatternsCompat
import br.com.fornaro.mesanews.domain.exceptions.InvalidEmailException

class ValidEmailUseCase {

    fun execute(email: String) {
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches())
            throw InvalidEmailException()
    }
}