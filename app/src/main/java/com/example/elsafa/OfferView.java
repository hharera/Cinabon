package com.example.elsafa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Offer.CompleteOffer;

public class OfferView extends AppCompatActivity {

    private FirebaseAuth auth;

    private ImageView wish, cart;
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

        wish = findViewById(R.id.wish);
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
                    wish.setImageResource(R.drawable.wished);
                }
                desc.setText(completeOffer.getDesc());
                price.setText(completeOffer.getPrice() + " EGP");
                List<Blob> pics = completeOffer.getProductPics();
                productPics.setAdapter(new ProductPicturesRecyclerViewAdapter(pics, OfferView.this));
                new CountDownTimer((completeOffer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) * 1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        long days = millisUntilFinished / 86400000;
                        millisUntilFinished = millisUntilFinished % 86400000;
                        long hours =  millisUntilFinished / 3600000;
                        millisUntilFinished = millisUntilFinished % 60000;
                        long miens = millisUntilFinished / 60000;
                        endTime.setText(days + " days " + hours + " hours "  + miens + " Miens");
                    }

                    public void onFinish() {
                        endTime.setText("Offer is Expired");
                    }
                }.start();

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
        Map<String, String> carts = completeOffer.getCarts();
        if (carts.containsKey(auth.getUid())) {
            carts.remove(auth.getUid());
            completeOffer.setCarts(carts);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("carts", carts);
            cart.setImageResource(R.drawable.cart);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersCart = (Map<String, Integer>) ds.get("offersCart");
                            offersCart.remove(offerId);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .update("offersCart", offersCart);
                        }
                    });

        } else {
            carts.put(auth.getUid(), null);
            completeOffer.setCarts(carts);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("carts", carts);
            cart.setImageResource(R.drawable.carted);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersCart = (Map<String, Integer>) ds.get("offersCart");
                            offersCart.put(offerId, 1);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .update("offersCart", offersCart);
                        }
                    });
        }
    }


    public void addToWishList(View view) {
        Map<String, String> wishes = completeOffer.getWishes();
        if (wishes.containsKey(auth.getUid())) {
            wishes.remove(auth.getUid());
            completeOffer.setWishes(wishes);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("wishes", wishes);
            wish.setImageResource(R.drawable.wish);
        } else {
            wishes.put(auth.getUid(), null);
            completeOffer.setWishes(wishes);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("wishes", wishes);
            wish.setImageResource(R.drawable.wished);
        }
    }

    public void goBack(View view) {
        finish();
    }
}