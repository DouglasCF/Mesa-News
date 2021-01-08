package br.com.fornaro.mesanews.domain.di

import br.com.fornaro.mesanews.domain.usecase.CreateAccountUseCase
import org.koin.dsl.module

private val useCaseModules = module {
    single { CreateAccountUseCase(authenticationRepository = get()) }
}

val domainModules = useCaseModules