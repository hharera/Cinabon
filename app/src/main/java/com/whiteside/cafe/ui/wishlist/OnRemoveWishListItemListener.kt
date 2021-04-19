package com.whiteside.cafe.ui.wishlist

interface OnRemoveWishListItemListener {
    open fun onRemoveWishListItemSuccess()
    open fun onRemoveWishListItemFailed(e: Exception)
}