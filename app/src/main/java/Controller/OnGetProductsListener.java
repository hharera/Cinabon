package Controller;

import Controller.CartRecyclerViewAdapter.ViewHolder;
import Model.Product.Product;

public interface OnGetProductsListener {

    void onSuccess(ViewHolder holder, Product product, int position);

    void onFailed(Exception e);
}
