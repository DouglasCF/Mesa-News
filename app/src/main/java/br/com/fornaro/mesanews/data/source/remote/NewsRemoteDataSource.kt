package br.com.fornaro.mesanews.data.source.remote

import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import br.com.fornaro.mesanews.data.source.remote.mappers.HighlightsRemoteMapper
import br.com.fornaro.mesanews.data.source.remote.mappers.NewsRemoteMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class NewsRemoteDataSource(
    private val api: MesaApi,
    private val highlightsMapper: HighlightsRemoteMapper,
    private val newsMapper: NewsRemoteMapper
) {

    suspend fun fetchHighlights(userToken: String) = flow {
        val response = api.fetchHighlights(userToken)
        emit(highlightsMapper.map(response))
    }

    suspend fun fetchNews(userToken: String) = flow {
        while (true) {
            val response = api.fetchNews(userToken)
            emit(newsMapper.map(response))
            delay(30 * 1000L)
        }
    }
}