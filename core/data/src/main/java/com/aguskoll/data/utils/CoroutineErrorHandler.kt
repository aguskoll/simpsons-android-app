package com.aguskoll.data.utils

import android.util.Log
import com.aguskoll.domain.errors.ErrorHandler
import com.aguskoll.domain.errors.ErrorNotification
import com.aguskoll.domain.errors.ErrorNotifier
import com.aguskoll.domain.errors.ErrorType
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

/**
 * CoroutineErrorHandler is a class that handles uncaught exceptions in coroutines.
 * For more information of how to use it go to [ErrorHandler].
 */
class CoroutineErrorHandler(
    private val errorNotifier: ErrorNotifier
) : ErrorHandler() {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        val errorNotification = when (exception) {
            // add your custom exceptions here
            is HttpException -> ErrorNotification(
                ErrorType.HttpException
            )
            is UnknownHostException -> ErrorNotification(
                ErrorType.NetworkError
            )
            else -> ErrorNotification(
                ErrorType.UnknownError(exception)
            )
        }

        Log.e("ErrorHandler", "Error happened: ${exception.message} ")

        errorNotifier.notify(errorNotification)
    }
}
