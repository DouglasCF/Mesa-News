package br.com.fornaro.mesanews.di

import br.com.fornaro.mesanews.data.di.dataModules
import br.com.fornaro.mesanews.domain.di.domainModules
import br.com.fornaro.mesanews.features.di.featuresModules

val allModules = dataModules +
        featuresModules +
        domainModules