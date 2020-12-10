package com.example.elsafa.ui.Products;

import Model.Product.Product;

public interface OnGetProductsListener {

    void onSuccess(Product product);

    void onFailed(Exception e);

    void onWishListIsEmpty(Boolean isEmpty);

}
