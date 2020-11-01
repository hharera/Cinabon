package com.example.elsafa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.List;

import Controller.LastOffersAdapter;
import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Offer.CompleteOffer;
import Model.Offer.Offer;

public class OfferView extends AppCompatActivity {

    private FirebaseAuth auth;

    private ImageView wishList, cart;
    private TextView desc, endTime, price;
    private ViewPager2 productPics;
    private String offerId;
    private FirebaseFirestore fStore;
    private CompleteOffer completeOffer;
    private Boolean isWished = false, isInCart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        offerId = getIntent().getExtras().getString("OfferId");

        wishList = findViewById(R.id.navigation_wishlist);
        cart = findViewById(R.id.cart);
        desc = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        endTime = findViewById(R.id.end_Time);
        price = findViewById(R.id.price);

        getTheOffer();
    }

    private void getTheOffer() {
        fStore.collection("Offers")
                .document(offerId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot ds) {
                completeOffer = ds.toObject(CompleteOffer.class);
                if (completeOffer.getCarts().containsKey(auth.getUid())) {
                    isInCart = true;
                    cart.setImageResource(R.drawable.carted);
                }
                if (completeOffer.getWishes().containsKey(auth.getUid())) {
                    isWished = true;
                    wishList.setImageResource(R.drawable.wished);
                }
                desc.setText(completeOffer.getDesc());
                price.setText(completeOffer.getPrice() + " EGP");
                List<Blob> pics = completeOffer.getProductPics();
                productPics.setAdapter(new ProductPicturesRecyclerViewAdapter(pics, OfferView.this));
                endTime.setText("Ends In " + ((completeOffer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) / 86400) + " days");
            }
        });
        hide();
    }
    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    public void addToCart(View view) {
    }

    public void addToWishList(View view) {

    }

    public void goBack(View view) {
        finish();
    }
}