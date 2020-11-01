package com.example.elsafa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Image;
import Model.Offer.CompleteOffer;
import Model.Offer.Offer;

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
            auth.signInAnonymously();
            Intent intent = new Intent(this, PreView.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void setOffer() {
        String[] resources = new String[]{"https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"
                , "https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg",
                "https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"};

        Bitmap[] images = new Bitmap[3];

        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Picasso.get().load(resources[i]).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    images[finalI] = bitmap;

                    Timestamp time = Timestamp.now();
                    CompleteOffer offer = new CompleteOffer();
                    offer.setDesc("Buy 2 cups of Black Coffee and take the third as a gift");
                    offer.setCarts(new HashMap<>());
                    offer.setDiscountPercentage(33);
                    offer.setStartTime(time);
                    offer.setEndTime(new Timestamp(time.getSeconds() + 86400, time.getNanoseconds()));

                    Bitmap offerPic = (images[finalI]);
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
}