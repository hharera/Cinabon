package com.whiteside.cafe.common


interface BaseListener<T> {
    fun onSuccess(result: T)
    fun onFailed(exception: Exception)
    fun onLoading()
}