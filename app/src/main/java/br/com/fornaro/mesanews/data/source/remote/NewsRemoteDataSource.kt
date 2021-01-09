package br.com.fornaro.mesanews.data.source.remote

import br.com.fornaro.mesanews.data.source.remote.api.MesaApi
import br.com.fornaro.mesanews.data.source.remote.mappers.HighlightsRemoteMapper
import br.com.fornaro.mesanews.data.source.remote.mappers.NewsRemoteMapper
import br.com.fornaro.mesanews.domain.models.News

class NewsRemoteDataSource(
    private val api: MesaApi,
    private val highlightsMapper: HighlightsRemoteMapper,
    private val newsMapper: NewsRemoteMapper
) {

    suspend fun fetchHighlights(userToken: String): List<News> {
        val response = api.fetchHighlights(userToken)
        return highlightsMapper.map(response)
    }

    suspend fun fetchNews(userToken: String): List<News> {
        val response = api.fetchNews(userToken)
        return newsMapper.map(response)
    }
}