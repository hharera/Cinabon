package com.whiteside.cafe.Cart

import Model.Item

interface OnGetCartItem {
    open fun onGetCartItemSuccess(item: Item?)
    open fun onGetCartItemFailed(e: Exception?)
    open fun onCartIsEmpty()
}