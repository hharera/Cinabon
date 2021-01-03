package com.whiteside.cafe.Cart;

import Model.Item;

public interface OnGetCartItem {

    void onGetCartItemSuccess(Item item);

    void onGetCartItemFailed(Exception e);

    void onCartIsEmpty();

}
