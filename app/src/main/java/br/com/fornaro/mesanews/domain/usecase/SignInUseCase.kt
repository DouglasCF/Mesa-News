package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.data.repository.AuthenticationRepository
import br.com.fornaro.mesanews.domain.models.Authentication

class SignInUseCase(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(email: String, password: String): Authentication {
        return authenticationRepository.signIn(email, password)
    }
}