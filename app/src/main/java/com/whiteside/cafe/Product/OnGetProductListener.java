package com.whiteside.cafe.Product;

import Model.Product;

public interface OnGetProductListener {

    void onGetProductSuccess(Product product);

    void onGetProductFailed(Exception e);
}
