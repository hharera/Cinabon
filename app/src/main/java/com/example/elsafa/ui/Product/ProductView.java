package com.example.elsafa.ui.Product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elsafa.Cart.CartPresenter;
import com.example.elsafa.Cart.OnCartChangedListener;
import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Model.Item;
import Model.Product.CompleteProduct;

public class ProductView extends AppCompatActivity implements OnCartChangedListener, OnGetProductListener {

    private FirebaseAuth auth;
    private ImageView wish, cart, back;
    private TextView title, price;
    private ViewPager2 productPics;
    private String productId, categoryName;
    private FirebaseFirestore fStore;
    private CompleteProduct product;
    private CartPresenter cartPresenter;

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

        ProductPresenter productPresenter = new ProductPresenter(this);
        productPresenter.getProductInfo(categoryName, productId);
        cartPresenter = new CartPresenter(this);
    }

    public void cartClicked(View view) {
        if (product.getCarts().containsKey(auth.getUid())) {
            cartPresenter.removeItem(product);
        } else {
            cartPresenter.addItem(product);
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

    @Override
    public void onRemoveItemSuccess() {
        cart.setImageResource(R.drawable.cart);
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddItemSuccess() {
        cart.setImageResource(R.drawable.carted);
        Toast.makeText(this, "Offer added to the cart", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRemoveItemFailed() {

    }

    @Override
    public void onAddItemFailed() {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetProductSuccess(CompleteProduct product) {
        this.product = product;
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

    @Override
    public void onGetProductFailed(Exception e) {

    }
}