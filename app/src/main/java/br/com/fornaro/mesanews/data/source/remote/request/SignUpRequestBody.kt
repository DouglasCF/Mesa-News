package br.com.fornaro.mesanews.data.source.remote.request

data class SignUpRequestBody(
    val name: String,
    val email: String,
    val password: String
)
