package com.whiteside.cafe.ui.wishList

interface OnAddWishListItem {
    open fun onAddWishListItemSuccess()
    open fun onAddWishListItemFailed(e: Exception?)
}