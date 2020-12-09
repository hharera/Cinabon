package com.example.elsafa.ui.Cart;

import Model.Item;

public interface OnGetItemsListener {

    void onSuccess(Item item);

    void onFailed(Exception e);

    void onWishListIsEmpty(Boolean isEmpty);

}
