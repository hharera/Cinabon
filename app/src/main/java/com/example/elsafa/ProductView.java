package com.example.elsafa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Product.CompleteProduct;
import Model.Product.Product;

public class ProductView extends AppCompatActivity {

    private FirebaseAuth auth;

    private ImageView wish, cart;
    private TextView title, price;
    private ViewPager2 productPics;
    private String productId, categoryName;
    private FirebaseFirestore fStore;
    private CompleteProduct completeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        productId = getIntent().getExtras().getString("productId");
        categoryName = getIntent().getExtras().getString("category");

        wish = findViewById(R.id.wish);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        price = findViewById(R.id.price);

        getProduct();
    }

    private void getProduct() {
        fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .document(productId)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot ds) {
                completeProduct = ds.toObject(CompleteProduct.class);
                if (completeProduct.getCarts().containsKey(auth.getUid())) {
                    cart.setImageResource(R.drawable.carted);
                }
                if (completeProduct.getWishes().containsKey(auth.getUid())) {
                    wish.setImageResource(R.drawable.wished);
                }
                title.setText(completeProduct.getTitle());
                price.setText(completeProduct.getPrice() + " EGP");
                List<Blob> pics = completeProduct.getProductPics();
                productPics.setAdapter(new ProductPicturesRecyclerViewAdapter(pics, ProductView.this));
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
        Map<String, String> carts = completeProduct.getCarts();
        if (carts.containsKey(auth.getUid())) {
            carts.remove(auth.getUid());
            completeProduct.setCarts(carts);
            fStore.collection("Offers")
                    .document(productId)
                    .update("carts", carts);
            cart.setImageResource(R.drawable.cart);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersCart = (Map<String, Integer>) ds.get("offersCart");
                            offersCart.remove(productId);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .update("offersCart", offersCart);
                        }
                    });

        } else {
            carts.put(auth.getUid(), null);
            completeProduct.setCarts(carts);
            fStore.collection("Offers")
                    .document(productId)
                    .update("carts", carts);
            cart.setImageResource(R.drawable.carted);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot ds) {
                            Map<String, Integer> offersCart = (Map<String, Integer>) ds.get("offersCart");
                            offersCart.put(productId, 1);
                            fStore.collection("Users")
                                    .document(auth.getUid())
                                    .update("offersCart", offersCart);
                        }
                    });
        }
    }


    public void addToWishList(View view) {
        Map<String, String> wishes = completeProduct.getWishes();
        if (wishes.containsKey(auth.getUid())) {
            wishes.remove(auth.getUid());
            completeProduct.setWishes(wishes);
            fStore.collection("Offers")
                    .document(productId)
                    .update("wishes", wishes);
            wish.setImageResource(R.drawable.wish);
        } else {
            wishes.put(auth.getUid(), null);
            completeProduct.setWishes(wishes);
            fStore.collection("Offers")
                    .document(productId)
                    .update("wishes", wishes);
            wish.setImageResource(R.drawable.wished);
        }
    }

    public void goBack(View view) {
        finish();
    }
}