package com.whiteside.cafe.ui.wishlist

import com.whiteside.cafe.model.Item

interface OnGetWishListItem {
    open fun onGetWishListItemSuccess(item: Item)
    open fun onGetWishListItemFailed(e: Exception)
    open fun onWishListIsEmpty()
}