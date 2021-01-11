package br.com.fornaro.mesanews.features.filter.di

import br.com.fornaro.mesanews.features.filter.FilterDialogViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel {
        FilterDialogViewModel(
            applyFilterPreferenceUseCase = get()
        )
    }
}

val filterModules = viewModelModules