package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.enums.FeedFilter

class ApplyFilterPreferenceUseCase(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(filter: FeedFilter) {
        newsRepository.applyFilter(filter)
    }
}