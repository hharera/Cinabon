package com.example.elsafa.ui.shop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elsafa.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Controller.OffersRecyclerViewAdapter;
import Model.Category;
import Model.Offer;

public class ShopFragment extends Fragment {

    private FirebaseAuth auth;
    private ViewPager2 best_offers_pager, last_offers_pager;
    private FirebaseFirestore fStore;
    private List<Category> offers;
    private SpringDotsIndicator best_dots_indicator, last_dots_indicator;

    public ShopFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        offers = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        last_offers_pager = root.findViewById(R.id.last_offers_pager);
        best_offers_pager = root.findViewById(R.id.best_offers_pager);

        best_dots_indicator = root.findViewById(R.id.best_dots_indicator);
        last_dots_indicator = root.findViewById(R.id.last_dots_indicator);

        getOffers();

        return root;
    }

    private void getOffers() {
        final List<Offer> bestOffers = new ArrayList();
//        fStore.collection("Offers")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot v, @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            e.printStackTrace();
//                        } else {
//                            for (DocumentSnapshot ds : v.getDocuments()) {
//                                final Offer offer = ds.toObject(Offer.class);
//                                offers.add(offer);
//                            }
//
//                        }
//                    }
//                });

        Category[] categories = new Category[]{
                new Category( "Drinks", R.drawable.drinks),
                new Category( "Meals", R.drawable.meals),
                new Category( "Salads", R.drawable.salads),
                new Category( "Sandwiches", R.drawable.sandwiches),
                new Category( "Sweets", R.drawable.sweets),
                new Category( "Snacks", R.drawable.snacks)
        };

        this.offers = (List<Category>) Arrays.asList(categories);

        OffersRecyclerViewAdapter offersRecyclerViewAdapter = new OffersRecyclerViewAdapter(offers, getContext());
        last_offers_pager.setAdapter(offersRecyclerViewAdapter);
        best_offers_pager.setAdapter(offersRecyclerViewAdapter);

        best_dots_indicator.setViewPager2(best_offers_pager);
        last_dots_indicator.setViewPager2(last_offers_pager);
    }

    private void categoriseOffers() {
//        List<Offer> los = offers;
//        Collections.sort(los);
//        List<Offer> offers1 = new ArrayList();
//        for (int i = 0; i < 5 && i < los.size(); i++) {
//            offers1.add(los.get(i));
//        }
//        OffersRecyclerViewAdapter offersRecyclerViewAdapter = new OffersRecyclerViewAdapter(offers1, getContext());
//        last_offers_pager.setAdapter(offersRecyclerViewAdapter);
//        best_dots_indicator.setViewPager2(best_offers_pager);
//        last_dots_indicator.setViewPager2(last_offers_pager);
    }

    private void setOffer() {
        Offer offer = new Offer();
        offer.setTime(Timestamp.now());

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meals);
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, o);
        offer.setOffer(Blob.fromBytes(o.toByteArray()));

        fStore.collection("Offers").document().set(offer);
    }
}