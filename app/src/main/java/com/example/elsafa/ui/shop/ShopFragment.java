package com.example.elsafa.ui.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;

import Controller.OffersViewFlipperAdapter;
import Model.Offer;

public class ShopFragment extends Fragment {

    FirebaseAuth auth;
    AdapterViewFlipper view_flipper_offers;
    DatabaseReference dRef;
    StorageReference sRef;

    List<Offer> offers;

    public ShopFragment() {
        auth = FirebaseAuth.getInstance();
        dRef = FirebaseDatabase.getInstance().getReference();
        sRef = FirebaseStorage.getInstance().getReference();
        offers = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        view_flipper_offers = root.findViewById(R.id.view_flipper_offers);
//        setOffer();
        getOffers();
        return root;
    }

    private void setOffer() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.offer1);
        ByteArrayOutputStream offer = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, offer);
        List<Bitmap> list = new ArrayList<>();
        list.add(bitmap);
        view_flipper_offers.setAdapter(new OffersViewFlipperAdapter(list, getContext()));

//        sRef.child("Offers").child(String.valueOf(1)).putBytes(offer.toByteArray());
    }

    private void getOffers() {
        dRef.child("Offers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()) {
                    final Offer offer = new Offer();
                    offer.setOID(s.child("OID").getValue(Integer.class));
                    offer.setPID(s.child("PID").getValue(Integer.class));
                    sRef.child("Offers").child(String.valueOf(1)).getBytes((1024 * 1024))
                            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    offer.setBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                                    offers.add(offer);
                                    List<Bitmap> list = new ArrayList<>();
                                    list.add(offer.getBitmap());
                                    view_flipper_offers.setAdapter(new OffersViewFlipperAdapter(list, getContext()));
                                    view_flipper_offers.notifyAll();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getOffers();
    }
}