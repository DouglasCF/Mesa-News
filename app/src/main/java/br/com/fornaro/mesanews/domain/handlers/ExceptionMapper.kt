package br.com.fornaro.mesanews.domain.handlers

import br.com.fornaro.mesanews.data.source.remote.api.NoConnectivityException
import br.com.fornaro.mesanews.domain.enums.ErrorType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.full.isSubclassOf

class ExceptionMapper(
    private val handler: (ErrorType) -> Unit
) : AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {

    private val exceptions = listOf(
        Pair(NoConnectivityException::class, ErrorType.NO_INTERNET),
    )

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        var error = exceptions.firstOrNull { exception::class.isSubclassOf(it.first) }?.second

        if (error == null) {
            error = ErrorType.GENERIC_ERROR
            // Log Firebase Crashlytics
        }

        handler.invoke(error)
    }
}