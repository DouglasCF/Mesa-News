package br.com.fornaro.mesanews.domain.di

import br.com.fornaro.mesanews.domain.usecase.*
import org.koin.dsl.module

private val useCaseModules = module {
    single { CreateAccountUseCase(authenticationRepository = get()) }
    single { ValidConfirmPasswordUseCase() }
    single { ValidPasswordUseCase() }
    single { ValidEmailUseCase() }
    single { ValidNameUseCase() }
}

val domainModules = useCaseModules