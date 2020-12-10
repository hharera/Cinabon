package com.example.elsafa.ui.Products;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import Model.Product.Product;

public class CategoryProductsPresenter {

    OnGetProductsListener listener;

    public CategoryProductsPresenter(OnGetProductsListener listener) {
        this.listener = listener;
    }

    private void getProductsFromFirebase(String categoryName) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot v) {
                        for (DocumentSnapshot ds : v.getDocuments()) {
                            Product product = ds.toObject(Product.class);
                            product.setProductId(ds.getId());

                            listener.onSuccess(product);
                        }
                    }
                });
    }

    public void getProducts(String categoryName) {
        getProductsFromFirebase(categoryName);
    }
}