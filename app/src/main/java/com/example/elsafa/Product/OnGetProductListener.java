package com.example.elsafa.Product;

import Model.Product;

public interface OnGetProductListener {

    void onGetProductSuccess(Product product);

    void onGetProductFailed(Exception e);
}
