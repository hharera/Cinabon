package com.example.elsafa.ui.WishList;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import Model.Item;

public class WishListItemsPresenter {

    OnGetItemsListener listener;

    public WishListItemsPresenter(OnGetItemsListener listener) {
        this.listener = listener;
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
                            listener.onWishListIsEmpty(task.getResult().getDocuments().isEmpty());

                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                listener.onSuccess(ds.toObject(Item.class));
                            }
                        } else {
                            listener.onFailed(task.getException());
                        }
                    }
                });


    }

    public void getWishListItems() {
        getItemsFromFirebase();
    }
}