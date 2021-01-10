package br.com.fornaro.mesanews.domain.exceptions

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
        Pair(InvalidNameException::class, ErrorType.INVALID_NAME),
        Pair(InvalidEmailException::class, ErrorType.INVALID_EMAIL),
        Pair(InvalidPasswordException::class, ErrorType.INVALID_PASSWORD),
        Pair(InvalidConfirmPasswordException::class, ErrorType.INVALID_CONFIRM_PASSWORD),
        Pair(UserNotLoggedInException::class, ErrorType.USER_NOT_LOGGED_IN)
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