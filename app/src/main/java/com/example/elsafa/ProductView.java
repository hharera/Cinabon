package com.example.elsafa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Item;
import Model.Product.CompleteProduct;

public class ProductView extends AppCompatActivity {

    private FirebaseAuth auth;
    private ImageView wish, cart, back;
    private TextView title, price;
    private ViewPager2 productPics;
    private String productId, categoryName;
    private FirebaseFirestore fStore;
    private CompleteProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        productId = getIntent().getExtras().getString("productId");
        categoryName = getIntent().getExtras().getString("categoryName");

        back = findViewById(R.id.back);
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
                product = ds.toObject(CompleteProduct.class);
                product.setProductId(ds.getId());

                if (product.getCarts().containsKey(auth.getUid())) {
                    cart.setImageResource(R.drawable.carted);
                }
                if (product.getWishes().containsKey(auth.getUid())) {
                    wish.setImageResource(R.drawable.wished);
                }
                title.setText(product.getTitle());
                price.setText(product.getPrice() + " EGP");
                List<Blob> pics = product.getProductPics();
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

    public void cartClicked(View view) {
        Map<String, Integer> carts = product.getCarts();
        if (carts.containsKey(auth.getUid())) {
            carts.remove(auth.getUid());
            product.setCarts(carts);

            fStore.collection("Categories")
                    .document(categoryName)
                    .collection("Products")
                    .document(productId)
                    .update("carts", carts);

            cart.setImageResource(R.drawable.cart);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(categoryName + productId)
                    .delete();

            Toast.makeText(this, "Offer removed from the cart", Toast.LENGTH_SHORT).show();
        } else {
            carts.put(auth.getUid(), 1);
            product.setCarts(carts);

            fStore.collection("Categories")
                    .document(categoryName)
                    .collection("Products")
                    .document(productId)
                    .update("carts", carts);

            cart.setImageResource(R.drawable.carted);

            Item item = new Item();
            item.setTime(Timestamp.now());
            item.setCategoryName(categoryName);
            item.setProductId(productId);
            item.setQuantity(1);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("Cart")
                    .document(categoryName + productId)
                    .set(item);

            Toast.makeText(this, "Offer Added to the cart", Toast.LENGTH_SHORT).show();
        }
    }


    public void wishClicked(View view) {
        Map<String, Integer> wishList = product.getWishes();
        if (wishList.containsKey(auth.getUid())) {
            wishList.remove(auth.getUid());
            product.setWishes(wishList);

            fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("wishes", wishList);

            wish.setImageResource(R.drawable.wish);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document(product.getCategoryName() + product.getProductId())
                    .delete();

            Toast.makeText(this, "Product removed to the WishList", Toast.LENGTH_SHORT).show();

        } else {
            wishList.put(auth.getUid(), 1);
            product.setCarts(wishList);

            fStore.collection("Categories")
                    .document(product.getCategoryName())
                    .collection("Products")
                    .document(product.getProductId())
                    .update("wishes", wishList);

            wish.setImageResource(R.drawable.wished);

            Item item = new Item();
            item.setTime(Timestamp.now());
            item.setCategoryName(product.getCategoryName());
            item.setProductId(product.getProductId());
            item.setQuantity(1);

            fStore.collection("Users")
                    .document(auth.getUid())
                    .collection("WishList")
                    .document(product.getCategoryName() + product.getProductId())
                    .set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ProductView.this, "Product added from the WishList", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goBack(View view) {
        finish();
    }
}