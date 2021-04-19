package com.whiteside.cafe.common


abstract class BaseListener<T> {

    abstract fun onSuccess(result: T)
    fun onFailed(exception: Exception) {
        exception.printStackTrace()
    }
}