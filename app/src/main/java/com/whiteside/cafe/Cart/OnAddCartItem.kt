package com.whiteside.cafe.Cart

interface OnAddCartItem {
    open fun onAddCartItemSuccess()
    open fun onAddCartItemFailed(e: Exception?)
}