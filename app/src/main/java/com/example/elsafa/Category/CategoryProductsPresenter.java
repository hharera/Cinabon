package com.example.elsafa.Category;

import com.example.elsafa.Product.OnGetProductListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import Model.Product;

public class CategoryProductsPresenter {

    OnGetProductListener listener;

    public CategoryProductsPresenter(OnGetProductListener listener) {
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

                            listener.onGetProductSuccess(product);
                        }
                    }
                });
    }

    public void getProducts(String categoryName) {
        getProductsFromFirebase(categoryName);
    }
}