package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.domain.models.Authentication

class CreateAccountUseCase(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(name: String, email: String, password: String): Authentication {
        return authenticationRepository.signUp(name, email, password)
    }
}