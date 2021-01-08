package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.domain.exceptions.InvalidNameException

class ValidNameUseCase {

    fun execute(name: String) {
        if (name.isBlank()) throw InvalidNameException()
    }
}