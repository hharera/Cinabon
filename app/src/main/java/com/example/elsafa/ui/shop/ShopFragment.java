package com.example.elsafa.ui.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Controller.BestOffersAdapter;
import Controller.LastOffersAdapter;
import Model.Offer.BestOffer;
import Model.Offer.LastOffer;
import Model.Offer.Offer;

public class ShopFragment extends Fragment {

    private SpringDotsIndicator best_dots_indicator, last_dots_indicator;

    private List<LastOffer> lastOffers;
    private List<BestOffer> bestOffers;

    private FirebaseFirestore fStore;

    private ViewPager2 best_offers_pager, last_offers_pager;


    public ShopFragment() {
        fStore = FirebaseFirestore.getInstance();
        lastOffers = new ArrayList();
        bestOffers = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        last_offers_pager = root.findViewById(R.id.last_offers_pager);
        best_offers_pager = root.findViewById(R.id.best_offers_pager);

        last_offers_pager.setAdapter(new LastOffersAdapter(new ArrayList<>(), getActivity()));
        best_offers_pager.setAdapter(new BestOffersAdapter(new ArrayList<>(), getActivity()));

        best_dots_indicator = root.findViewById(R.id.best_dots_indicator);
        last_dots_indicator = root.findViewById(R.id.last_dots_indicator);

        best_dots_indicator.setViewPager2(best_offers_pager);
        last_dots_indicator.setViewPager2(last_offers_pager);

        getOffers();

        return root;
    }

    private void getOffers() {
        fStore.collection("Offers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot v) {
                        for (DocumentSnapshot ds : v.getDocuments()) {
                            final LastOffer lastOffer = ds.toObject(LastOffer.class);
                            final BestOffer bestOffer = ds.toObject(BestOffer.class);

                            lastOffer.setOfferId(ds.getId());
                            bestOffer.setOfferId(ds.getId());

                            lastOffers.add(lastOffer);
                            bestOffers.add(bestOffer);
                        }
                        getLastOffers();
                        getBestOffers();
                    }
                });
    }

    private void getBestOffers() {
        Collections.sort(bestOffers);
        best_offers_pager.setAdapter(new BestOffersAdapter(bestOffers, getActivity()));
        best_dots_indicator.setViewPager2(best_offers_pager);
    }

    private void getLastOffers() {
        Collections.sort(lastOffers);
        last_offers_pager.setAdapter(new LastOffersAdapter(lastOffers, getActivity()));
        last_dots_indicator.setViewPager2(last_offers_pager);
    }
}