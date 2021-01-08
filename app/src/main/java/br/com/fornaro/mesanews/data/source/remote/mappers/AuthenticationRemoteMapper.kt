package br.com.fornaro.mesanews.data.source.remote.mappers

import br.com.fornaro.mesanews.data.source.remote.response.SignUpResponse
import br.com.fornaro.mesanews.domain.models.Authentication

typealias AuthenticationRemoteMapperAlias = Mapper<SignUpResponse, Authentication>

object AuthenticationRemoteMapper : Mapper<SignUpResponse, Authentication> {
    override fun map(input: SignUpResponse) = with(input) {
        Authentication(token = token)
    }
}