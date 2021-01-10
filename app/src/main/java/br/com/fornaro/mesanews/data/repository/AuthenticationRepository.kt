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

    val isUserLogged get() = !localDataSource.email.isNullOrBlank()

    suspend fun getToken() = withContext(dispatcherMap.io) { localDataSource.getToken() }

    suspend fun signUp(name: String, email: String, password: String) =
        withContext(dispatcherMap.io) {
            remoteDataSource.signUp(name = name, email = email, password = password)
                .also { saveUser(email, it.token) }
        }

    suspend fun signIn(email: String, password: String) =
        withContext(dispatcherMap.io) {
            remoteDataSource.signIn(email = email, password = password)
                .also { saveUser(email, it.token) }
        }

    private suspend fun saveUser(email: String, token: String) {
        localDataSource.saveUser(email, token)
    }
}