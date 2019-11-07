package io.github.diov.msaider

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-13.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

@Suppress("NOTHING_TO_INLINE")
sealed class Outcome<T> {
    data class Success<T>(val value: T) : Outcome<T>()
    data class Failure<T>(val exception: Throwable) : Outcome<T>()

    companion object {
        inline fun <T> success(value: T): Outcome<T> =
            Success(value)

        inline fun <T> failure(exception: Throwable): Outcome<T> =
            Failure(exception)
    }
}
