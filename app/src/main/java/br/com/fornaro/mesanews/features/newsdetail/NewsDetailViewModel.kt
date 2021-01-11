package br.com.fornaro.mesanews.features.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.domain.usecase.UpdateFavoriteUseCase
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val news: News,
    private val newsRepository: NewsRepository,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModel() {

    private val _state = MutableLiveData<NewsDetailState>()
    val state: LiveData<NewsDetailState> get() = _state

    fun favoriteNews() {
        viewModelScope.launch {
            updateFavoriteUseCase.execute(news)
            (state.value as NewsDetailState.Success).let {
                _state.value = NewsDetailState.Success(isFavorite = news.isFavorite)
            }
        }
    }

    fun checkFavorite() {
        viewModelScope.launch {
            _state.value = NewsDetailState.Success(isFavorite = newsRepository.isFavorite(news))
        }
    }

    fun share() {
        _state.value = NewsDetailState.Share(news)
    }
}

sealed class NewsDetailState {
    data class Success(val isFavorite: Boolean) : NewsDetailState()
    data class Share(val news: News) : NewsDetailState()
}