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
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Controller.OffersPagerAdapter;
import Model.Offer.Offer;

public class ShopFragment extends Fragment implements OnGetOffersListener {

    private SpringDotsIndicator best_dots_indicator, last_dots_indicator;

    private final List<Offer> lastOffers;
    private final List<Offer> bestOffers;


    private final FirebaseFirestore fStore;
    private OffersPagerAdapter lastOffersPagerAdapter;
    private OffersPagerAdapter bestOffersPagerAdapter;

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

        best_dots_indicator = root.findViewById(R.id.best_dots_indicator);
        last_dots_indicator = root.findViewById(R.id.last_dots_indicator);

        lastOffersPagerAdapter = new OffersPagerAdapter(lastOffers, getActivity());
        bestOffersPagerAdapter = new OffersPagerAdapter(bestOffers, getActivity());

        last_offers_pager.setAdapter(lastOffersPagerAdapter);
        best_offers_pager.setAdapter(bestOffersPagerAdapter);

        best_dots_indicator.setViewPager2(best_offers_pager);
        last_dots_indicator.setViewPager2(last_offers_pager);

        TransitionInflater inflater1 = TransitionInflater.from(getContext());
        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));


        OffersPresenter presenter = new OffersPresenter(this);
        presenter.getOffers();

        return root;
    }


    private void refreshBestOffers(Offer offer) {
        offer.type = 1;
        bestOffers.add(offer);
        Collections.sort(bestOffers);
        bestOffersPagerAdapter.notifyDataSetChanged();
        best_dots_indicator.setViewPager2(best_offers_pager);
    }

    private void refreshLastOffers(Offer offer) {
        offer.type = 2;
        lastOffers.add(offer);
        Collections.sort(lastOffers);
        lastOffersPagerAdapter.notifyDataSetChanged();
        last_dots_indicator.setViewPager2(last_offers_pager);
    }

    @Override
    public void onSuccess(Offer offer) {
        refreshLastOffers(offer);
        refreshBestOffers(offer);
    }

    @Override
    public void onFailed(Exception e) {
        if (e != null) {
            e.printStackTrace();
        }
    }
}