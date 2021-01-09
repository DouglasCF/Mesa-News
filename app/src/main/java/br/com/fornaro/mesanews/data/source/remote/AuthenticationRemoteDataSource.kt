package br.com.fornaro.mesanews.data.source.remote

import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import br.com.fornaro.mesanews.data.source.remote.mappers.SignInRemoteMapperAlias
import br.com.fornaro.mesanews.data.source.remote.mappers.SignUpRemoteMapperAlias
import br.com.fornaro.mesanews.data.source.remote.request.SignInRequestBody
import br.com.fornaro.mesanews.data.source.remote.request.SignUpRequestBody
import br.com.fornaro.mesanews.domain.models.Authentication

class AuthenticationRemoteDataSource(
    private val api: MesaApi,
    private val signUpMapper: SignUpRemoteMapperAlias,
    private val signInMapper: SignInRemoteMapperAlias
) {

    suspend fun signUp(name: String, email: String, password: String): Authentication {
        val requestBody = SignUpRequestBody(
            name = name,
            email = email,
            password = password
        )
        val response = api.signUp(requestBody)
        return signUpMapper.map(response)
    }

    suspend fun signIn(email: String, password: String): Authentication {
        val requestBody = SignInRequestBody(
            email = email,
            password = password
        )
        val response = api.signIn(requestBody)
        return signInMapper.map(response)
    }
}