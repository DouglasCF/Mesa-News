package br.com.fornaro.mesanews.features.createaccount.di

import br.com.fornaro.mesanews.features.createaccount.CreateAccountViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val viewModelModules = module {
    viewModel {
        CreateAccountViewModel(
            createAccountUseCase = get(),
            validConfirmPasswordUseCase = get(),
            validEmailUseCase = get(),
            validNameUseCase = get(),
            validPasswordUseCase = get()
        )
    }
}

val createAccountModules = viewModelModules