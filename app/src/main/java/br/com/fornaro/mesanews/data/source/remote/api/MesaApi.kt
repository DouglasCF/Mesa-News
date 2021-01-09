package br.com.fornaro.mesanews.data.source.remote.api

import br.com.fornaro.mesanews.data.source.remote.request.SignInRequestBody
import br.com.fornaro.mesanews.data.source.remote.request.SignUpRequestBody
import br.com.fornaro.mesanews.data.source.remote.response.HighlightsResponse
import br.com.fornaro.mesanews.data.source.remote.response.SignInResponse
import br.com.fornaro.mesanews.data.source.remote.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val AUTHORIZATION = "Authorization"

interface MesaApi {

    @POST("client/auth/signup")
    suspend fun signUp(@Body body: SignUpRequestBody): SignUpResponse

    @POST("client/auth/signin")
    suspend fun signIn(@Body body: SignInRequestBody): SignInResponse

    @GET("client/news/highlights")
    suspend fun fetchHighlights(@Header(AUTHORIZATION) userToken: String): HighlightsResponse
}