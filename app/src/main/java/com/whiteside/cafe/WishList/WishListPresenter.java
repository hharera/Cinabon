package com.whiteside.cafe.WishList;

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

public class WishListPresenter {

    private final FirebaseFirestore fStore;
    private final FirebaseAuth auth;
    OnGetWishListItem onGetWishListItem;
    OnAddWishListItem onAddWishListItem;
    OnRemoveWishListItemListener onRemoveWishListItemListener;


    public WishListPresenter() {
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    public void setOnGetWishListItem(OnGetWishListItem onGetWishListItem) {
        this.onGetWishListItem = onGetWishListItem;
    }

    public void setOnAddWishListItem(OnAddWishListItem onAddWishListItem) {
        this.onAddWishListItem = onAddWishListItem;
    }

    public void setOnRemoveWishListItemListener(OnRemoveWishListItemListener onRemoveWishListItemListener) {
        this.onRemoveWishListItemListener = onRemoveWishListItemListener;
    }


    private void getItemsFromFirebase() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        fStore.collection("Users")
                .document(auth.getUid())
                .collection("WishList")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                onGetWishListItem.onWishListIsEmpty();
                            }

                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                onGetWishListItem.onGetWishListItemSuccess(ds.toObject(Item.class));
                            }
                        } else {
                            onGetWishListItem.onGetWishListItemFailed(task.getException());
                        }
                    }
                });
    }

    public void getWishListItems() {
        getItemsFromFirebase();
    }

    public void addItem(Product product) {
        Map<String, Integer> wishList = product.getWishes();
        wishList.put(auth.getUid(), 1);
        product.setWishes(wishList);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("wishes", wishList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onAddWishListItem.onAddWishListItemSuccess();
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
                        .collection("WishList")
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
        Map<String, Integer> wishList = product.getWishes();
        wishList.remove(auth.getUid());

        product.setWishes(wishList);

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Categories")
                        .document(product.getCategoryName())
                        .collection("Products")
                        .document(product.getProductId())
                        .update("wishes", wishList)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                onRemoveWishListItemListener.onRemoveWishListItemSuccess();
                            }
                        });
            }
        };

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                fStore.collection("Users")
                        .document(auth.getUid())
                        .collection("WishList")
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
}