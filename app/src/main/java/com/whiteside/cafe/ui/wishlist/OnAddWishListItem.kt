package com.whiteside.cafe.ui.wishlist

interface OnAddWishListItem {
    fun onAddWishListItemSuccess()
    fun onAddWishListItemFailed(e: Exception)
}