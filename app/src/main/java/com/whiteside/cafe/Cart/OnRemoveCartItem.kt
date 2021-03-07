package com.whiteside.cafe.Cart;

public interface OnRemoveCartItem {

    void onRemoveCartItemSuccess();

    void onRemoveCartItemFailed(Exception e);
}
