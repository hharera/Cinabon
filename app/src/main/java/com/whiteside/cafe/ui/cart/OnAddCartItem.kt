package com.whiteside.cafe.ui.cart

interface OnAddCartItem {
    open fun onAddCartItemSuccess()
    open fun onAddCartItemFailed(e: Exception)
}