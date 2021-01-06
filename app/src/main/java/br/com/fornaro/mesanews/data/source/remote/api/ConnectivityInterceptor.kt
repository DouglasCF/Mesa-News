package br.com.fornaro.mesanews.data.source.remote.api

import android.content.Context
import br.com.fornaro.mesanews.data.extensions.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = if (context.isNetworkConnected()) {
        chain.proceed(chain.request())
    } else {
        throw NoConnectivityException()
    }
}

class NoConnectivityException : IOException()