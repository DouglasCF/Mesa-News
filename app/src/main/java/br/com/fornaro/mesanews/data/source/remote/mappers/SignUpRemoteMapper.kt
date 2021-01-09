package br.com.fornaro.mesanews.data.source.remote.mappers

import br.com.fornaro.mesanews.data.source.remote.response.SignUpResponse
import br.com.fornaro.mesanews.domain.models.Authentication

object SignUpRemoteMapper : Mapper<SignUpResponse, Authentication> {
    override fun map(input: SignUpResponse) = with(input) {
        Authentication(token = token)
    }
}