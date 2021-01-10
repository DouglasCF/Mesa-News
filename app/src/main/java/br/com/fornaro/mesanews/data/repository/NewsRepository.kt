package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.local.NewsLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.models.News
import kotlinx.coroutines.withContext

class NewsRepository(
    private val authenticationRepository: AuthenticationRepository,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val dispatcherMap: DispatcherMap
) {

    suspend fun getHighlightsNews() = withContext(dispatcherMap.io) {
        newsRemoteDataSource.fetchHighlights(authenticationRepository.getToken())
            .map { it.copy(isFavorite = newsLocalDataSource.isFavorite(it.title)) }
    }

    suspend fun getNews() = withContext(dispatcherMap.io) {
        newsRemoteDataSource.fetchNews(authenticationRepository.getToken())
            .map { it.copy(isFavorite = newsLocalDataSource.isFavorite(it.title)) }
    }

    suspend fun updateFavoriteNews(news: News, isFavorite: Boolean) =
        withContext(dispatcherMap.io) {
            newsLocalDataSource.updateFavorite(news.title, isFavorite)
        }
}