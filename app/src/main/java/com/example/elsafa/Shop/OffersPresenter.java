package com.example.elsafa.Shop;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import Model.Offer;

public class OffersPresenter {

    OnGetOffersListener listener;

    public OffersPresenter(OnGetOffersListener listener) {
        this.listener = listener;
    }

    private void getOffersFromFirebase() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        fStore.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot ds : task.getResult().getDocuments()) {
                                Offer offer = ds.toObject(Offer.class);
                                offer.setOfferId(ds.getId());
                                listener.onGetOfferSuccess(offer);
                            }
                        } else {
                            listener.onGetOfferFailed(task.getException());
                        }
                    }
                });
    }

    public void getOffers() {
        getOffersFromFirebase();
    }
}
