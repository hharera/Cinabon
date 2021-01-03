package com.whiteside.cafe.Category;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SimpleOnSearchActionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.whiteside.cafe.Product.OnGetProductListener;
import com.whiteside.cafe.R;
import com.whiteside.cafe.Search.SearchResults;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Product;


public class CategoryProducts extends AppCompatActivity implements OnGetProductListener {

    private static final String TAG = "CategoryProducts";
    private String categoryName;
    private List<Product> itemList;
    private FirebaseFirestore fStore;
    private CategoryProductsRecyclerView adapter;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        searchBar = findViewById(R.id.search_view);

        fStore = FirebaseFirestore.getInstance();
        categoryName = getIntent().getExtras().getString("category");

        RecyclerView recyclerView = findViewById(R.id.products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        adapter = new CategoryProductsRecyclerView(itemList, this);
        recyclerView.setAdapter(adapter);

        CategoryProductsPresenter presenter = new CategoryProductsPresenter(this);
        presenter.getProducts(categoryName);

        setSearchListeners();
    }

    private void setSearchListeners() {
        searchBar.setOnSearchActionListener(new SimpleOnSearchActionListener() {
            @Override
            public void onSearchConfirmed(CharSequence text) {
                Intent intent = new Intent(CategoryProducts.this, SearchResults.class);
                intent.putExtra("text", String.valueOf(text));
                startActivity(intent);
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
                Product product = new Product();
                product.setTitle("Buy 2 cups of Black Coffee and take the third as a gift");
                product.setCarts(new HashMap<>());
                product.setWishes(new HashMap<>());
                product.setCategoryName(categoryName);
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

    @Override
    public void onGetProductSuccess(Product product) {
        itemList.add(product);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetProductFailed(Exception e) {

    }
}