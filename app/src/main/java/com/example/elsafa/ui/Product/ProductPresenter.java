package com.example.elsafa.ui.Product;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.Product.CompleteProduct;

public class ProductPresenter {

    private final OnGetProductListener listener;

    public ProductPresenter(OnGetProductListener listener) {
        this.listener = listener;
    }

    private void getProductFromFirebase(String categoryName, String productId) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .document(productId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        CompleteProduct product = ds.toObject(CompleteProduct.class);
                        product.setProductId(ds.getId());
                        listener.onGetProductSuccess(product);
                    }
                });
    }

    public void getProductInfo(String categoryName, String productID) {
        getProductFromFirebase(categoryName, productID);
    }
}