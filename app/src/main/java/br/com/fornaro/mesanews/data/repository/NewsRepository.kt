package br.com.fornaro.mesanews.data.repository

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import br.com.fornaro.mesanews.data.source.local.NewsLocalDataSource
import br.com.fornaro.mesanews.data.source.remote.NewsRemoteDataSource
import br.com.fornaro.mesanews.domain.enums.FeedFilter
import br.com.fornaro.mesanews.domain.models.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

@FlowPreview
@ExperimentalCoroutinesApi
class NewsRepository(
    private val authenticationRepository: AuthenticationRepository,
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource,
    private val dispatcherMap: DispatcherMap
) {

    private var allNews = emptyList<News>()

    private var filter = FeedFilter.DATE

    private val _highlights = ConflatedBroadcastChannel<List<News>>()
    val highlights get() = _highlights.asFlow()

    private val _news = ConflatedBroadcastChannel<List<News>>()
    val news
        get() = _news.asFlow()
            .map {
                when (filter) {
                    FeedFilter.DATE -> it.sortedByDescending { news -> news.publishedAt.time }
                    FeedFilter.FAVORITE -> it.filter { news -> news.isFavorite }
                }
            }

    suspend fun getHighlightsNews() =
        newsRemoteDataSource.fetchHighlights(authenticationRepository.getToken())
            .onEach { it.forEach { news -> news.isFavorite = isFavorite(news) } }
            .flowOn(dispatcherMap.io)
            .collect { _highlights.sendBlocking(it) }

    suspend fun getNews() =
        newsRemoteDataSource.fetchNews(authenticationRepository.getToken())
            .onEach { it.forEach { news -> news.isFavorite = isFavorite(news) } }
            .flowOn(dispatcherMap.io)
            .collect {
                allNews = it
                _news.sendBlocking(it)
            }

    suspend fun updateFavoriteNews(news: News, isFavorite: Boolean) {
        newsLocalDataSource.updateFavorite(news.title, isFavorite)

        allNews.filter { it.title == news.title }
            .onEach { it.isFavorite = news.isFavorite }
        _highlights.sendBlocking(allNews.filter { it.isHighlight })

        allNews.filter { it.title == news.title }
            .onEach { it.isFavorite = news.isFavorite }
        _news.sendBlocking(allNews)
    }

    suspend fun isFavorite(news: News) = withContext(dispatcherMap.io) {
        newsLocalDataSource.isFavorite(news.title)
    }

    fun applyFilter(filter: FeedFilter) {
        this.filter = filter
        _news.sendBlocking(allNews)
    }
}