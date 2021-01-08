package br.com.fornaro.mesanews.data.source.remote

import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import br.com.fornaro.mesanews.data.source.remote.mappers.AuthenticationRemoteMapperAlias
import br.com.fornaro.mesanews.data.source.remote.request.SignUpRequestBody
import br.com.fornaro.mesanews.domain.models.Authentication

class AuthenticationRemoteDataSource(
    private val api: MesaApi,
    private val mapper: AuthenticationRemoteMapperAlias
) {

    suspend fun signUp(name: String, email: String, password: String): Authentication {
        val requestBody = SignUpRequestBody(
            name = name,
            email = email,
            password = password
        )
        val response = api.signUp(requestBody)
        return mapper.map(response)
    }
}