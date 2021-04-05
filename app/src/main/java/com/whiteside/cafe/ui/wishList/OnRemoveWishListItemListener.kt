package com.whiteside.cafe.ui.wishList

interface OnRemoveWishListItemListener {
    open fun onRemoveWishListItemSuccess()
    open fun onRemoveWishListItemFailed(e: Exception)
}