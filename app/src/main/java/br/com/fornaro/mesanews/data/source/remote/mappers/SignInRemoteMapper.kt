package br.com.fornaro.mesanews.data.source.remote.mappers

import br.com.fornaro.mesanews.data.source.remote.response.SignInResponse
import br.com.fornaro.mesanews.domain.models.Authentication

object SignInRemoteMapper : Mapper<SignInResponse, Authentication> {
    override fun map(input: SignInResponse) = with(input) {
        Authentication(token = token)
    }
}