package com.hsdroid.mockflix.util

sealed class ResponseState<out T> {
    object LOADING : ResponseState<Nothing>()
    class SUCCESS<T>(val data: T) : ResponseState<T>()
    class FAILURE<T>(val t: Throwable) : ResponseState<T>()
}
