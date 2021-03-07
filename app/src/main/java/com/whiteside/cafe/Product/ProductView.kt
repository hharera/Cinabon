package com.whiteside.cafe.Product;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.whiteside.cafe.Cart.CartPresenter;
import com.whiteside.cafe.Cart.OnAddCartItem;
import com.whiteside.cafe.Cart.OnRemoveCartItem;
import com.whiteside.cafe.ProductPicturesRecyclerViewAdapter;
import com.whiteside.cafe.R;
import com.whiteside.cafe.WishList.OnAddWishListItem;
import com.whiteside.cafe.WishList.OnRemoveWishListItemListener;
import com.whiteside.cafe.WishList.WishListPresenter;

import java.util.List;

import Model.Product;

public class ProductView extends AppCompatActivity implements OnRemoveCartItem,
        OnAddCartItem, OnAddWishListItem, OnRemoveWishListItemListener, OnGetProductListener {

    private FirebaseAuth auth;
    private ImageView wish, cart;
    private TextView title, price;
    private ViewPager2 productPics;
    private Product product;
    private CartPresenter cartPresenter;
    private WishListPresenter wishListPresenter;
    private ProductPresenter productPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        auth = FirebaseAuth.getInstance();

        String productId = getIntent().getExtras().getString("productId");
        String categoryName = getIntent().getExtras().getString("categoryName");

        wish = findViewById(R.id.wish);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        price = findViewById(R.id.price);

        productPresenter = new ProductPresenter();
        productPresenter.setListener(this);
        productPresenter.getProductInfo(categoryName, productId);

        cartPresenter = new CartPresenter();
        cartPresenter.setOnRemoveCartItem(this);
        cartPresenter.setOnAddCartItem(this);

        wishListPresenter = new WishListPresenter();
        wishListPresenter.setOnRemoveWishListItemListener(this);
        wishListPresenter.setOnAddWishListItem(this);
    }

    public void cartClicked(View view) {
        if (product.getCarts().containsKey(auth.getUid())) {
            cartPresenter.removeItem(product);
        } else {
            cartPresenter.addItem(product);
        }
    }


    public void wishClicked(View view) {
        if (product.getWishes().containsKey(auth.getUid())) {
            wishListPresenter.removeItem(product);
        } else {
            wishListPresenter.addItem(product);
        }
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onRemoveCartItemSuccess() {
        cart.setImageResource(R.drawable.cart);
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveCartItemFailed(Exception e) {

    }

    @Override
    public void onAddCartItemSuccess() {
        cart.setImageResource(R.drawable.carted);
        Toast.makeText(this, "Offer added to the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCartItemFailed(Exception e) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetProductSuccess(Product product) {
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

    @Override
    public void onAddWishListItemSuccess() {
        wish.setImageResource(R.drawable.wished);
        Toast.makeText(ProductView.this, "Product added to the WishList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWishListItemFailed(Exception e) {

    }

    @Override
    public void onRemoveWishListItemSuccess() {
        wish.setImageResource(R.drawable.wish);
        Toast.makeText(this, "Product removed to the WishList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveWishListItemFailed(Exception e) {

    }
}