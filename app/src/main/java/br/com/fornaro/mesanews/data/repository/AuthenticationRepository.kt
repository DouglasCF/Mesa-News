package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.local.AuthenticationLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.AuthenticationRemoteDataSource
import kotlinx.coroutines.withContext

class AuthenticationRepository(
    private val remoteDataSource: AuthenticationRemoteDataSource,
    private val localDataSource: AuthenticationLocalDataSource,
    private val dispatcherMap: DispatcherMap
) {

    val token get() = localDataSource.token

    val isUserLogged get() = !token.isNullOrBlank()

    suspend fun signUp(name: String, email: String, password: String) =
        withContext(dispatcherMap.io) {
            remoteDataSource.signUp(name = name, email = email, password = password)
                .also { saveToken(it.token) }
        }

    suspend fun signIn(email: String, password: String) =
        withContext(dispatcherMap.io) {
            remoteDataSource.signIn(email = email, password = password)
                .also { saveToken(it.token) }
        }

    private fun saveToken(token: String) {
        localDataSource.token = token
    }
}