package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.source.local.AuthenticationLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.AuthenticationRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource
) {

    val token get() = localDataSource.token

    val isUserLogged get() = !token.isNullOrBlank()

    suspend fun signUp(name: String, email: String, password: String) =
        withContext(Dispatchers.IO) {
            remoteDataSource.signUp(name = name, email = email, password = password)
                .also { saveToken(it.token) }
        }

    private fun saveToken(token: String) {
        localDataSource.token = token
    }
}