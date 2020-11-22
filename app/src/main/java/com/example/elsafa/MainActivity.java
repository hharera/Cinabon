package com.example.elsafa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

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

import Model.Offer.Offer;
import Model.User;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore fStore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition);
        TextView cafe_name = findViewById(R.id.cafe_name);
        cafe_name.startAnimation(animation);

        if (auth.getCurrentUser() == null) {
            signInAnonymously();
        } else {
            Thread waitingPage = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (Exception e) {
                    } finally {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            waitingPage.start();
        }
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
        finish();
    }

    private void addUserToFirebase() {
        User user = new User();
        user.setName("NA");
        user.setPhoneNumber(-1);
        user.setUID(auth.getUid());

        fStore.collection("Users")
                .document(auth.getUid())
                .set(user);

        fStore.collection("Users")
                .document(auth.getUid())
                .collection("Cart");

        fStore.collection("Users")
                .document(auth.getUid())
                .collection("WishList");
    }

    private void setOffer(int i) {
        Uri[] imagesURL = new Uri[]{
                Uri.parse("https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg")
                , Uri.parse("https://addconn.com/images/limited.png")};


        String[] productsIds = new String[]{
                "xZvcdItmvKWqilQ9ikbm",
                "Lnslv4jK5o7GFrITi5vm"
        };

        Picasso.get().load(imagesURL[i]).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Timestamp time = Timestamp.now();

                Offer offer = new Offer();
                offer.setProductId(productsIds[i]);
                offer.setDiscountPercentage(33);
                offer.setStartTime(time);
                offer.setEndTime(new Timestamp(time.getSeconds() + 86400, time.getNanoseconds()));
                offer.setCategoryName("Meals");

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                offer.setOfferPic(Blob.fromBytes(stream.toByteArray()));

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