package com.whiteside.cafe.WishList

import Model.Item

interface OnGetWishListItem {
    open fun onGetWishListItemSuccess(item: Item?)
    open fun onGetWishListItemFailed(e: Exception?)
    open fun onWishListIsEmpty()
}