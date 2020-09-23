package com.example.elsafa.ui.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Controller.OffersViewFlipperAdapter;
import Model.Offer;

public class ShopFragment extends Fragment {

    FirebaseAuth auth;
    AdapterViewFlipper best_offers_flipper, last_offers_flipper;
    DatabaseReference dRef;
    StorageReference sRef;

    List<Offer> offers;
    List<Bitmap> list;

    public ShopFragment() {
        auth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference();
        sRef = FirebaseStorage.getInstance().getReference();
        offers = new ArrayList();
        list = new ArrayList<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        last_offers_flipper = root.findViewById(R.id.last_offers_flipper);
        best_offers_flipper = root.findViewById(R.id.best_offers_flipper);

//        setOffer();
        getBestOffers();
        getLastOffers();

        return root;
    }

    private void getBestOffers() {
        final List<Offer>  bestOffers = new ArrayList();
        dRef.child("Offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                final int[] i = {1};
                for (DataSnapshot s : snapshot.getChildren()) {
                    final Offer offer = new Offer();
                    offer.setOID(s.child("OID").getValue(Integer.class));
                    offer.setPID(s.child("PID").getValue(Integer.class));
                    offer.setDate(s.child("Date").getValue(Date.class));
                    offer.setPercentage(s.child("Percentage").getValue(Float.class));

                    sRef.child("Offers").child(String.valueOf(offer.getOID())).getBytes((1024 * 1024))
                            .addOnCompleteListener(new OnCompleteListener<byte[]>() {
                                @Override
                                public void onComplete(@NonNull Task<byte[]> task) {
                                    if (task.isSuccessful()) {
                                        offer.setBitmap(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                                        bestOffers.add(offer);
                                        if (snapshot.getChildrenCount() == i[0]) {
                                            best_offers_flipper.setAdapter(new OffersViewFlipperAdapter(bestOffers, getContext()));
                                            best_offers_flipper.setFlipInterval(1500);
                                            best_offers_flipper.canScrollHorizontally(View.TEXT_DIRECTION_RTL);
                                            best_offers_flipper.startFlipping();
                                        }
                                        i[0]++;
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getLastOffers() {
        final List<Offer> lastOffers = new ArrayList();
        dRef.child("Offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                final int[] i = {1};
                for (DataSnapshot s : snapshot.getChildren()) {
                    final Offer offer = new Offer();
                    offer.setOID(s.child("OID").getValue(Integer.class));
                    offer.setPID(s.child("PID").getValue(Integer.class));
                    offer.setDate(s.child("Date").getValue(Date.class));
                    offer.setPercentage(s.child("Percentage").getValue(Float.class));

                    sRef.child("Offers").child(String.valueOf(offer.getOID())).getBytes((1024 * 1024))
                            .addOnCompleteListener(new OnCompleteListener<byte[]>() {
                                @Override
                                public void onComplete(@NonNull Task<byte[]> task) {
                                    if (task.isSuccessful()) {
                                        offer.setBitmap(BitmapFactory.decodeByteArray(task.getResult(), 0, task.getResult().length));
                                        lastOffers.add(offer);
                                        if (snapshot.getChildrenCount() == i[0]) {
                                            last_offers_flipper.setAdapter(new OffersViewFlipperAdapter(lastOffers, getContext()));
                                            last_offers_flipper.setFlipInterval(1500);
                                            last_offers_flipper.canScrollHorizontally(View.TEXT_DIRECTION_RTL);
                                            last_offers_flipper.startFlipping();
                                        }
                                        i[0]++;
                                    }
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setOffer() {
        DatabaseReference reference = dRef.child("Offers").push();
        reference.child("OID").setValue(2);
        reference.child("PID").setValue(2);
        reference.child("Date").setValue(new Date());
        reference.child("Percentage").setValue(55.5);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.offer2);
        ByteArrayOutputStream offer = new ByteArrayOutputStream();
        getReducedBitmap(bitmap, 512).compress(Bitmap.CompressFormat.PNG, 50, offer);

        sRef.child("Offers").child(2 + "").putBytes(offer.toByteArray());
    }

    public Bitmap getReducedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}