package br.com.fornaro.mesanews.features.feed.di

import br.com.fornaro.mesanews.features.feed.FeedViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel {
        FeedViewModel(
            newsRepository = get(),
            updateFavoriteUseCase = get()
        )
    }
}

val feedModules = viewModelModules