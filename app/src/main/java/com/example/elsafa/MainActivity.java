package com.example.elsafa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Offer.CompleteOffer;
import Model.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.waiting_page);

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            signInAnonymously();
        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

//        setOffer();
        finish();
    }

    private void signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                addUserToFirebase();
            }
        });

        Intent intent = new Intent(this, PreView.class);
        startActivity(intent);
    }

    private void addUserToFirebase() {
        User user = new User();
        user.setName("NA");
        user.setOffersCart(new HashMap<>());
        user.setOffersWishList(new HashMap<>());
        user.setProductsCart(new HashMap<>());
        user.setProductsWishList(new HashMap<>());
        user.setPhoneNumber(-1);
        user.setUID(auth.getUid());

        fStore.collection("Users")
                .document(auth.getUid())
                .set(user);
    }

    private void setOffer() {
        String[] resources = new String[]{"https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"
                , "https://addconn.com/images/limited.png",
                "http://www.cullinsyard.co.uk/wp-content/uploads/2018/01/special-offer.jpg"};

        Bitmap[] images = new Bitmap[3];


        Picasso.get().load(resources[1]).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                images[1] = bitmap;

                Timestamp time = Timestamp.now();
                CompleteOffer offer = new CompleteOffer();
                offer.setTitle("Buy 2 cups of Black Coffee and take the third as a gift");
                offer.setCarts(new HashMap<>());
                offer.setDiscountPercentage(33);
                offer.setStartTime(time);
                offer.setEndTime(new Timestamp(time.getSeconds() + 86400, time.getNanoseconds()));

                Bitmap offerPic = (images[1]);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                offerPic.compress(Bitmap.CompressFormat.PNG, 100, stream);
                offer.setOfferPic(Blob.fromBytes(stream.toByteArray()));

                List<Blob> blobs = new ArrayList<>();
                blobs.add(Blob.fromBytes(stream.toByteArray()));
                offer.setProductPics(blobs);
                offer.setWishes(new HashMap<>());
                offer.setPrice(20);

                fStore.collection("Offers").document().set(offer);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }
}