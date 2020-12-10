package com.example.elsafa.ui.Product;

import Model.Product.CompleteProduct;

public interface OnGetProductListener {

    void onGetProductSuccess(CompleteProduct product);

    void onGetProductFailed(Exception e);

}
