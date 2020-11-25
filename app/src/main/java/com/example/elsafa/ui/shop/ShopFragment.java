package com.example.elsafa.ui.shop;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Controller.OffersPagerAdapter;
import Model.Offer.Offer;

public class ShopFragment extends Fragment {

    private SpringDotsIndicator best_dots_indicator, last_dots_indicator;

    private List<Offer> lastOffers;
    private List<Offer> bestOffers;


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

        last_offers_pager.setAdapter(new OffersPagerAdapter(new ArrayList<>(), getActivity()));
        best_offers_pager.setAdapter(new OffersPagerAdapter(new ArrayList<>(), getActivity()));

        best_dots_indicator = root.findViewById(R.id.best_dots_indicator);
        last_dots_indicator = root.findViewById(R.id.last_dots_indicator);

        best_dots_indicator.setViewPager2(best_offers_pager);
        last_dots_indicator.setViewPager2(last_offers_pager);

        TransitionInflater inflater1 = TransitionInflater.from(getContext());
        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));

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
                            Offer offer = ds.toObject(Offer.class);

                            offer.type = 1;
                            bestOffers.add(offer);

                            offer.type = 2;
                            lastOffers.add(offer);
                        }
                        getLastOffers();
                        getBestOffers();
                    }
                });
    }

    private void getBestOffers() {
        Collections.sort(bestOffers);
        List<Offer> offers = new ArrayList<>();
        offers.addAll(bestOffers);
        best_offers_pager.setAdapter(new OffersPagerAdapter(offers, getActivity()));
        best_dots_indicator.setViewPager2(best_offers_pager);
    }

    private void getLastOffers() {
        Collections.sort(lastOffers);
        List<Offer> offers = new ArrayList<>();
        offers.addAll(bestOffers);
        last_offers_pager.setAdapter(new OffersPagerAdapter(offers, getActivity()));
        last_dots_indicator.setViewPager2(last_offers_pager);
    }
}