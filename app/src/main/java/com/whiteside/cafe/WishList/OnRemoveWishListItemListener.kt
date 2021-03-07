package com.whiteside.cafe.WishList

interface OnRemoveWishListItemListener {
    open fun onRemoveWishListItemSuccess()
    open fun onRemoveWishListItemFailed(e: Exception?)
}