package com.whiteside.cafe.Product;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Model.Item;
import Model.Product;

public class ProductPresenter {

    OnGetProductListener listener;

    public ProductPresenter() {
    }

    public void setListener(OnGetProductListener listener) {
        this.listener = listener;
    }

    private void getProductFromFirebase(Item item) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        fStore.collection("Categories")
                .document(item.getCategoryName())
                .collection("Products")
                .document(item.getProductId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot ds = task.getResult();
                            Product product = ds.toObject(Product.class);
                            product.setProductId(ds.getId());
                            listener.onGetProductSuccess(product);
                        } else {
                            listener.onGetProductFailed(task.getException());
                        }
                    }
                });
    }

    public void getProduct(Item item) {
        getProductFromFirebase(item);
    }

    public void getProductInfo(String categoryName, String productId) {
        Item item = new Item();
        item.setCategoryName(categoryName);
        item.setProductId(productId);

        getProduct(item);
    }
}