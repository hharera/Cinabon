package com.harera.common.base

interface BaseListener<T> {
    fun onSuccess(result: T)
    fun onFailed(exception: Exception)
    fun onLoading()
}