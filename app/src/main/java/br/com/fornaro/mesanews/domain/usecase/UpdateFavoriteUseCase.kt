package br.com.fornaro.mesanews.domain.usecase

import br.com.fornaro.mesanews.data.repository.NewsRepository
import br.com.fornaro.mesanews.domain.models.News

class UpdateFavoriteUseCase(
    private val newsRepository: NewsRepository
) {

    suspend fun execute(news: News) {
        news.isFavorite = !news.isFavorite
        newsRepository.updateFavoriteNews(news, news.isFavorite)
    }
}