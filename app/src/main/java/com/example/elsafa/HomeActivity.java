package com.example.elsafa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.elsafa.Search.SearchResults;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SimpleOnSearchActionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

import Model.Offer;

public class HomeActivity extends AppCompatActivity {

    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        searchBar = findViewById(R.id.search_view);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_shop, R.id.navigation_categories, R.id.navigation_account, R.id.navigation_cart, R.id.navigation_wishlist)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        serSearchListeners();

    }

    private void serSearchListeners() {
        searchBar.setOnSearchActionListener(new SimpleOnSearchActionListener() {
            @Override
            public void onSearchConfirmed(CharSequence text) {
                super.onSearchConfirmed(text);
                Intent intent = new Intent(HomeActivity.this, SearchResults.class);
                intent.putExtra("text", String.valueOf(text));
                startActivity(intent);
            }
        });
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

                FirebaseFirestore fStore = FirebaseFirestore.getInstance();
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