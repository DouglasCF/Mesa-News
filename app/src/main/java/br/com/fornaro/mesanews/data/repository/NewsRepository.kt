package br.com.fornaro.mesanews.data.repository

import android.util.Log
import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.local.NewsLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.models.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

@FlowPreview
@ExperimentalCoroutinesApi
class NewsRepository(
    private val authenticationRepository: AuthenticationRepository,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val dispatcherMap: DispatcherMap
) {

    private val _highlights = ConflatedBroadcastChannel<List<News>>()
    val highlights get() = _highlights.asFlow()

    private val _news = ConflatedBroadcastChannel<List<News>>()
    val news get() = _news.asFlow()

    suspend fun getHighlightsNews() =
        newsRemoteDataSource.fetchHighlights(authenticationRepository.getToken())
            .onEach {
                it.forEach { news -> news.copy(isFavorite = isFavorite(news)) }
                Log.d("AAB", "onEach getHighlightsNews")
            }
            .flowOn(dispatcherMap.io)
            .collect {
                Log.d("AAB", "collect getHighlightsNews")
                _highlights.sendBlocking(it)
            }

    suspend fun getNews() =
        newsRemoteDataSource.fetchNews(authenticationRepository.getToken())
            .onEach {
                it.forEach { news -> news.copy(isFavorite = isFavorite(news)) }
                Log.d("AAB", "onEach getNews")
            }
            .flowOn(dispatcherMap.io)
            .collect {
                Log.d("AAB", "collect getNews")
                _news.sendBlocking(it)
            }

    suspend fun updateFavoriteNews(news: News, isFavorite: Boolean) {
        newsLocalDataSource.updateFavorite(news.title, isFavorite)

        _highlights.value.filter { it.title == news.title }
            .onEach { it.isFavorite = news.isFavorite }
        _highlights.sendBlocking(_highlights.value)

        _news.value.filter { it.title == news.title }
            .onEach { it.isFavorite = news.isFavorite }
        _news.sendBlocking(_news.value)
    }

    suspend fun isFavorite(news: News) = withContext(dispatcherMap.io) {
        newsLocalDataSource.isFavorite(news.title)
    }
}