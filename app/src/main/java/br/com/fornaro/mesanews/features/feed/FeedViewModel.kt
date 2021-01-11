package br.com.fornaro.mesanews.features.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.exceptions.ExceptionMapper
import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class FeedViewModel(
    private val newsRepository: NewsRepository,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    val content = FeedContent()

    private val _state = MutableLiveData<FeedState>()
    val state: LiveData<FeedState> get() = _state

    private val handler = ExceptionMapper { error ->
        _state.value = FeedState.Error(error)
    }

    init {
        getFeed()
        viewModelScope.launch(handler) {
            newsRepository.highlights.collect { news -> updateContent(highlights = news) }
        }
        viewModelScope.launch(handler) {
            newsRepository.news.collect { news -> updateContent(news = news) }
        }
    }

    fun getFeed() {
        _state.value = FeedState.Loading
        viewModelScope.launch(handler) { newsRepository.getHighlightsNews() }
        viewModelScope.launch(handler) { newsRepository.getNews() }
    }

    fun favoriteNews(news: News) {
        viewModelScope.launch {
            updateFavoriteUseCase.execute(news)
        }
    }

    private fun updateContent(highlights: List<News>? = null, news: List<News>? = null) {
        highlights?.let { content.highlights = it }
        news?.let { content.news = it }
        if (content.highlights.isNotEmpty() && content.news.isNotEmpty()) {
            _state.value = FeedState.Success(content)
        }
    }
}

data class FeedContent(
    var highlights: List<News> = emptyList(),
    var news: List<News> = emptyList()
)

sealed class FeedState {
    object Loading : FeedState()
    data class Error(val error: ErrorType) : FeedState()
    data class Success(val content: FeedContent) : FeedState()
}