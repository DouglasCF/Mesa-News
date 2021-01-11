package br.com.fornaro.mesanews.features.di

import br.com.fornaro.mesanews.features.createaccount.di.createAccountModules
import br.com.fornaro.mesanews.features.feed.di.feedModules
import br.com.fornaro.mesanews.features.filter.di.filterModules
import br.com.fornaro.mesanews.features.login.di.loginModules
import br.com.fornaro.mesanews.features.newsdetail.di.newsDetailModules

val featuresModules = loginModules +
        createAccountModules +
        feedModules +
        newsDetailModules +
        filterModules