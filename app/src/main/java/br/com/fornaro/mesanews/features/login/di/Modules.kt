package br.com.fornaro.mesanews.features.login.di

import br.com.fornaro.mesanews.features.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel { LoginViewModel() }
}

val loginModules = viewModelModules