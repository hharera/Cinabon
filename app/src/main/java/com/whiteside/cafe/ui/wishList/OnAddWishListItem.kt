package com.whiteside.cafe.ui.wishList

interface OnAddWishListItem {
    fun onAddWishListItemSuccess()
    fun onAddWishListItemFailed(e: Exception)
}