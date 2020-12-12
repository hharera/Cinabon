package com.example.elsafa.Cart;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import Model.Item;
import Model.Product;

public class CartPresenter {

    private final FirebaseFirestore fStore;
    private final FirebaseAuth auth;
    OnGetCartItem onGetCartItem;
    OnAddCartItem onAddCartItem;
    OnRemoveCartItem onRemoveCartItem;


    public CartPresenter() {
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    public void setOnGetCartItem(OnGetCartItem onGetCartItem) {
        this.onGetCartItem = onGetCartItem;
    }

    public void setOnAddCartItem(OnAddCartItem onAddCartItem) {
        this.onAddCartItem = onAddCartItem;
    }

    public void setOnRemoveCartItem(OnRemoveCartItem onRemoveCartItem) {
        this.onRemoveCartItem = onRemoveCartItem;
    }

    public void addItem(Product product) {
        Map<String, Integer> carts = product.getCarts();
        carts.put(auth.getUid(), 1);
        product.setCarts(carts);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("carts", carts)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onAddCartItem.onAddCartItemSuccess();
                            }
                        });
            }
        };


        Item item = new Item();
        item.setTime(Timestamp.now());
        item.setCategoryName(product.getCategoryName());
        item.setProductId(product.getProductId());
        item.setQuantity(1);

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("Cart")
                        .document(product.getCategoryName() + product.getProductId())
                        .set(item)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                thread2.start();
                            }
                        });
            }
        };

        thread1.start();
    }

    public void removeItem(Product product) {
        Map<String, Integer> carts = product.getCarts();
        carts.remove(auth.getUid());
        product.setCarts(carts);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("carts", carts)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onRemoveCartItem.onRemoveCartItemSuccess();
                            }
                        });
            }
        };

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("Cart")
                        .document(product.getCategoryName() + product.getProductId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                thread2.start();
                            }
                        });
            }
        };

        thread1.start();
    }


    private void getItemsFromFirebase() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                onGetCartItem.onCartIsEmpty();
                            }
                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                onGetCartItem.onGetCartItemSuccess(ds.toObject(Item.class));
                            }
                        } else {
                            onGetCartItem.onGetCartItemFailed(task.getException());
                        }
                    }
                });
    }

    public void updateQuantity(Item item, int quantity) {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart")
                .document(item.getCategoryName() + item.getProductId())
                .update("quantity", quantity);
    }

    public void getCartItems() {
        getItemsFromFirebase();
    }
}