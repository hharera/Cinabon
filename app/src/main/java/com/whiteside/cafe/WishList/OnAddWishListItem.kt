package com.whiteside.cafe.WishList

interface OnAddWishListItem {
    open fun onAddWishListItemSuccess()
    open fun onAddWishListItemFailed(e: Exception?)
}