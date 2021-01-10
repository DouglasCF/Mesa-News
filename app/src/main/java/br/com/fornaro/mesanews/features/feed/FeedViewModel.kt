package br.com.fornaro.mesanews.features.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.ErrorType
import br.com.fornaro.mesanews.domain.exceptions.ExceptionMapper
import br.com.fornaro.mesanews.domain.models.News
import kotlinx.coroutines.launch

class FeedViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableLiveData<FeedState>()
    val state: LiveData<FeedState> get() = _state

    fun getNews() {
        val handler = ExceptionMapper { error ->
            _state.value = FeedState.Error(error)
        }

        viewModelScope.launch(handler) {
            _state.value = FeedState.Loading
            val highlights = newsRepository.getHighlightsNews()
            val news = newsRepository.getNews()
            _state.value = FeedState.Success(highlights = highlights, news = news)
        }
    }
}

sealed class FeedState {
    object Loading : FeedState()
    data class Error(val error: ErrorType) : FeedState()
    data class Success(
        val highlights: List<News>,
        val news: List<News>
    ) : FeedState()
}