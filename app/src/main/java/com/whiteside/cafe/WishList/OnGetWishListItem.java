package com.whiteside.cafe.WishList;

import Model.Item;

public interface OnGetWishListItem {

    void onGetWishListItemSuccess(Item item);

    void onGetWishListItemFailed(Exception e);

    void onWishListIsEmpty();

}
