package com.whiteside.cafe.ui.cart

import com.whiteside.cafe.model.Item

interface OnGetCartItem {
    open fun onGetCartItemSuccess(item: Item)
    open fun onGetCartItemFailed(e: Exception)
    open fun onCartIsEmpty()
}