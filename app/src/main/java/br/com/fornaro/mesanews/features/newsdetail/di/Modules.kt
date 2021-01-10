package br.com.fornaro.mesanews.features.newsdetail.di

import br.com.fornaro.mesanews.domain.models.News
import br.com.fornaro.mesanews.features.newsdetail.NewsDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel { (news: News) ->
        NewsDetailViewModel(
            news = news,
            newsRepository = get(),
            updateFavoriteUseCase = get()
        )
    }
}

val newsDetailModules = viewModelModules