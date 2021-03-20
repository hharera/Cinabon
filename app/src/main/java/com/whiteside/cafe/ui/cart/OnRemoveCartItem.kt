package com.whiteside.cafe.ui.cart

interface OnRemoveCartItem {
    open fun onRemoveCartItemSuccess()
    open fun onRemoveCartItemFailed(e: Exception?)
}