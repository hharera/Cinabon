package com.example.elsafa.Cart;

public interface OnCartChangedListener {
    void onRemoveItemSuccess();

    void onAddItemSuccess();

    void onRemoveItemFailed();

    void onAddItemFailed();
}
