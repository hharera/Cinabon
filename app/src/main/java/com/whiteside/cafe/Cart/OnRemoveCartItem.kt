package com.whiteside.cafe.Cart

interface OnRemoveCartItem {
    open fun onRemoveCartItemSuccess()
    open fun onRemoveCartItemFailed(e: Exception?)
}