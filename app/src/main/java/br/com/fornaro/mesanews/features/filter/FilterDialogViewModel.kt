package br.com.fornaro.mesanews.features.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.FeedFilter
import br.com.fornaro.mesanews.domain.models.FilterContent
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

    fun applyFilter(filter: FeedFilter, textFilter: String) {
        viewModelScope.launch {
            applyFilterPreferenceUseCase.execute(filter, textFilter)
            _state.value = FilterState.Success
        }
    }
}

sealed class FilterState {
    data class Initial(val filter: FilterContent) : FilterState()
    object Success : FilterState()
}