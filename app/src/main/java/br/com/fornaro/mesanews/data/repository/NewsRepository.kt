package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.exceptions.InvalidTokenException
import kotlinx.coroutines.withContext

class NewsRepository(
    private val authenticationRepository: AuthenticationRepository,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val dispatcherMap: DispatcherMap
) {

    suspend fun getHighlightsNews() = withContext(dispatcherMap.io) {
        authenticationRepository.token?.let { newsRemoteDataSource.fetchHighlights(it) }
            ?: throw InvalidTokenException()
    }

    suspend fun getNews() = withContext(dispatcherMap.io) {
        authenticationRepository.token?.let { newsRemoteDataSource.fetchNews(it) }
            ?: throw InvalidTokenException()
    }
}