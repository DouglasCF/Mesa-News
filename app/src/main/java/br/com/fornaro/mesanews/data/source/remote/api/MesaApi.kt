package br.com.fornaro.mesanews.data.source.remote.api

import br.com.fornaro.mesanews.data.source.remote.request.SignUpRequestBody
import br.com.fornaro.mesanews.data.source.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MesaApi {

    @POST("client/auth/signup")
    suspend fun signUp(@Body body: SignUpRequestBody): SignUpResponse
}