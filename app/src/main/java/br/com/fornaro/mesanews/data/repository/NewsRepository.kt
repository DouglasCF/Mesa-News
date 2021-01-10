package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import kotlinx.coroutines.withContext

class NewsRepository(
    private val authenticationRepository: AuthenticationRepository,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val dispatcherMap: DispatcherMap
) {

    suspend fun getHighlightsNews() = withContext(dispatcherMap.io) {
        newsRemoteDataSource.fetchHighlights(authenticationRepository.getToken())

    }

    suspend fun getNews() = withContext(dispatcherMap.io) {
        newsRemoteDataSource.fetchNews(authenticationRepository.getToken())
    }
}