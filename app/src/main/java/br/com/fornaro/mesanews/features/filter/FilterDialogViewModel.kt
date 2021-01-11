package br.com.fornaro.mesanews.features.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.FeedFilter
import br.com.fornaro.mesanews.domain.usecase.ApplyFilterPreferenceUseCase
import kotlinx.coroutines.launch

class FilterDialogViewModel(
    private val newsRepository: NewsRepository,
    private val applyFilterPreferenceUseCase: ApplyFilterPreferenceUseCase
) : ViewModel() {

    private val _state = MutableLiveData<FilterState>()
    val state: LiveData<FilterState> get() = _state

    init {
        _state.value = FilterState.Initial(filter = newsRepository.filter)
    }

    fun filterNewsByDate() {
        viewModelScope.launch {
            applyFilterPreferenceUseCase.execute(FeedFilter.DATE)
            _state.value = FilterState.Success
        }
    }

    fun filterNewsByFavorite() {
        viewModelScope.launch {
            applyFilterPreferenceUseCase.execute(FeedFilter.FAVORITE)
            _state.value = FilterState.Success
        }
    }
}

sealed class FilterState {
    data class Initial(val filter: FeedFilter) : FilterState()
    object Success : FilterState()
}