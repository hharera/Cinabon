package com.example.elsafa.Cart;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import Model.Item;
import Model.Product.CompleteProduct;

public class CartPresenter {

    private final FirebaseFirestore fStore;
    private final FirebaseAuth auth;
    OnCartChangedListener listener;


    public CartPresenter(OnCartChangedListener listener) {
        this.listener = listener;
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void addItem(CompleteProduct product) {
        Map<String, Integer> carts = product.getCarts();
        carts.put(auth.getUid(), 1);
        product.setCarts(carts);

        Thread thread = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("carts", carts).isSuccessful();
            }
        };


        Item item = new Item();
        item.setTime(Timestamp.now());
        item.setCategoryName(product.getCategoryName());
        item.setProductId(product.getProductId());
        item.setQuantity(1);

        Thread thread1 = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("Cart")
                        .document(product.getCategoryName() + product.getProductId())
                        .set(item).isSuccessful();
            }
        };

        Boolean b1 = thread.isInterrupted();
        Boolean b2 = thread1.isInterrupted();

        if (b1 && b2) {
            listener.onAddItemSuccess();
        } else {
            listener.onAddItemFailed();
        }
    }

    public void removeItem(CompleteProduct product) {
        Map<String, Integer> carts = product.getCarts();
        carts.remove(auth.getUid());
        product.setCarts(carts);

        Thread thread = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("carts", carts).isSuccessful();
            }
        };


        Thread thread2 = new Thread() {
            @Override
            public boolean isInterrupted() {
                return fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("Cart")
                        .document(product.getCategoryName() + product.getProductId())
                        .delete().isSuccessful();
            }
        };

        Boolean aBoolean = thread.isInterrupted();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        aBoolean = aBoolean & thread2.isInterrupted();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (aBoolean) {
            listener.onRemoveItemSuccess();
        } else {
            listener.onRemoveItemFailed();
        }
    }
}