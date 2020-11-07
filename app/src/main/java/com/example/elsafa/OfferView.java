package com.example.elsafa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        hide();
    }

    private void getTheOffer() {
        fStore.collection("Offers")
                .document(offerId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot ds) {
                completeOffer = ds.toObject(CompleteOffer.class);
                if (completeOffer.getCarts().containsKey(auth.getUid())) {
                    cart.setImageResource(R.drawable.carted);
                }
                if (completeOffer.getWishes().containsKey(auth.getUid())) {
                    wish.setImageResource(R.drawable.wished);
                }
                desc.setText(completeOffer.getTitle());
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
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    public void cartClicked(View view) {
        Map<String, Integer> carts = completeOffer.getCarts();
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
                                    .update("offersCart",offersCart);
                        }
                    });
            Toast.makeText(this, "Offer removed from the cart", Toast.LENGTH_SHORT).show();
        } else {
            carts.put(auth.getUid(), 1);
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
            Toast.makeText(this, "Offer Added to the cart", Toast.LENGTH_SHORT).show();
        }
    }


    public void addToWishList(View view) {
        Map<String, Integer> wishList = completeOffer.getWishes();
        if (wishList.containsKey(auth.getUid())) {
            wishList.remove(auth.getUid());
            completeOffer.setWishes(wishList);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("wishes", wishList);
            wish.setImageResource(R.drawable.wish);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document("Offers")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersWish = (Map<String, Integer>) ds.get("offersWish");
                            offersWish.remove(offerId);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .collection("WishList")
                                    .document("Offers")
                                    .set(offersWish);
                        }
                    });
            Toast.makeText(this, "Offer removed from the wishlist", Toast.LENGTH_SHORT).show();
        } else {
            wishList.put(auth.getUid(), 1);
            completeOffer.setCarts(wishList);
            fStore.collection("Offers")
                    .document(offerId)
                    .update("wishes", wishList);
            wish.setImageResource(R.drawable.wished);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document("Offers")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersCart = (Map<String, Integer>) ds.get("offersWish");
                            offersCart.put(offerId, 1);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .collection("WishList")
                                    .document("Offers")
                                    .set(offersCart);
                        }
                    });
            Toast.makeText(this, "Offer Added to the wishlist", Toast.LENGTH_SHORT).show();
        }
    }

    public void goBack(View view) {
        finish();
    }
}