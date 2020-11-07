package com.example.elsafa;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Controller.CategoryProductsRecyclerView;
import Model.Offer.CompleteOffer;
import Model.Product.CompleteProduct;
import Model.Product.Product;


public class CategoryProducts extends AppCompatActivity {

    private static final String TAG = "CategoryProducts";
    private String categoryName;
    private List<Product> itemList;
    private FirebaseFirestore fStore;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        fStore = FirebaseFirestore.getInstance();
        categoryName = getIntent().getExtras().getString("category");

        recyclerView = findViewById(R.id.products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new CategoryProductsRecyclerView(itemList, this);
        recyclerView.setAdapter(adapter);

        getProducts();
        hide();

        for (int o = 0; o < 3; o++){
//            setOffer(o);
        }
    }

    private void getProducts() {
        fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot v) {
                        for (DocumentSnapshot ds : v.getDocuments()) {
                            Product product = ds.toObject(Product.class);
                            product.setProductId(ds.getId());
                            itemList.add(product);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void setOffer(int i) {
        String[] imagesURL = new String[]{"https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"
                , "https://addconn.com/images/limited.png",
                "http://www.cullinsyard.co.uk/wp-content/uploads/2018/01/special-offer.jpg"};


        Picasso.get().load(imagesURL[i]).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Timestamp time = Timestamp.now();
                CompleteProduct product = new CompleteProduct();
                product.setTitle("Buy 2 cups of Black Coffee and take the third as a gift");
                product.setCarts(new HashMap<>());
                product.setWishes(new HashMap<>());
                product.setCategory(categoryName);
                product.setPrice(20);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                product.setMainPic(Blob.fromBytes(stream.toByteArray()));

                List<Blob> blobs = new ArrayList<>();
                blobs.add(Blob.fromBytes(stream.toByteArray()));
                product.setProductPics(blobs);
                product.setWishes(new HashMap<>());
                product.setPrice(20);

                fStore.collection("Categories")
                        .document(categoryName)
                        .collection("Products")
                        .document()
                        .set(product);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private void hide() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}