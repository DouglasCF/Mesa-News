package br.com.fornaro.mesanews.features.di

import br.com.fornaro.mesanews.features.createaccount.di.createAccountModules
import br.com.fornaro.mesanews.features.login.di.loginModules

val featuresModules = loginModules +
        createAccountModules