package Controller.Cart;

import Controller.Cart.CartRecyclerViewAdapter.ViewHolder;
import Model.Product.Product;

public interface OnGetProductsListener {

    void onSuccess(ViewHolder holder, Product product, int position);

    void onFailed(Exception e);
}
