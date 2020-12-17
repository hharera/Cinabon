package com.example.elsafa.Offer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.elsafa.Cart.CartPresenter;
import com.example.elsafa.Cart.OnAddCartItem;
import com.example.elsafa.Cart.OnRemoveCartItem;
import com.example.elsafa.Product.OnGetProductListener;
import com.example.elsafa.Product.ProductPresenter;
import com.example.elsafa.ProductPicturesRecyclerViewAdapter;
import com.example.elsafa.R;
import com.example.elsafa.Shop.OnGetOffersListener;
import com.example.elsafa.WishList.OnAddWishListItem;
import com.example.elsafa.WishList.OnRemoveWishListItemListener;
import com.example.elsafa.WishList.WishListPresenter;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;

import java.util.List;

import Model.Offer;
import Model.Product;

public class OfferView extends AppCompatActivity implements OnGetOffersListener, OnAddCartItem, OnRemoveCartItem,
        OnAddWishListItem, OnRemoveWishListItemListener, OnGetProductListener {

    private FirebaseAuth auth;

    private ImageView wish, cart;
    private TextView title, endTime, price;
    private ViewPager2 productPics;
    private Product product;
    private Offer offer;
    private String offerId;
    private OfferPresenter offerPresenter;
    private ProductPresenter productPresenter;
    private CartPresenter cartPresenter;
    private WishListPresenter wishListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);

        auth = FirebaseAuth.getInstance();
        offerId = getIntent().getExtras().getString("offerId");

        wish = findViewById(R.id.wish);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        endTime = findViewById(R.id.end_Time);
        price = findViewById(R.id.price);

        productPresenter = new ProductPresenter();
        productPresenter.setListener(this);

        cartPresenter = new CartPresenter();
        cartPresenter.setOnAddCartItem(this);
        cartPresenter.setOnRemoveCartItem(this);

        wishListPresenter = new WishListPresenter();
        wishListPresenter.setOnAddWishListItem(this);
        wishListPresenter.setOnRemoveWishListItemListener(this);

        offerPresenter = new OfferPresenter(this);
        offerPresenter.getOffer(offerId);
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onGetOfferSuccess(Offer offer) {
        this.offer = offer;
        setEndTime();
        productPresenter.getProductInfo(offer.getCategoryName(), offer.getProductId());
    }

    @SuppressLint("SetTextI18n")
    private void setEndTime() {
        new CountDownTimer((offer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / 86400000;
                millisUntilFinished = millisUntilFinished % 86400000;
                long hours = millisUntilFinished / 3600000;
                millisUntilFinished = millisUntilFinished % 60000;
                long miens = millisUntilFinished / 60000;
                endTime.setText(days + " days " + hours + " hours " + miens + " Miens");
            }

            public void onFinish() {
                endTime.setText("Offer is Expired");
            }
        }.start();
        endTime.setText("Ends In " + ((offer.getEndTime().getSeconds() - Timestamp.now().getSeconds()) / 86400) + " days");
    }

    @Override
    public void onGetOfferFailed(Exception e) {

    }

    @Override
    public void onGetProductSuccess(Product product) {
        title.setText(product.getTitle());
        price.setText(product.getPrice() + " EGP");
        List<Blob> pics = product.getProductPics();
        productPics.setAdapter(new ProductPicturesRecyclerViewAdapter(pics, OfferView.this));

        if (product.getCarts().containsKey(auth.getUid())) {
            cart.setImageResource(R.drawable.carted);
        }

        if (product.getWishes().containsKey(auth.getUid())) {
            wish.setImageResource(R.drawable.wished);
        }

        this.product = product;
    }

    @Override
    public void onGetProductFailed(Exception e) {
        Toast.makeText(this, "Failed to load product", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveCartItemSuccess() {
        this.cart.setImageResource(R.drawable.cart);
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWishListItemSuccess() {
        wish.setImageResource(R.drawable.wished);
        Toast.makeText(this, "Product Added to the wishList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveCartItemFailed(Exception e) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddWishListItemFailed(Exception e) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCartItemSuccess() {
        this.cart.setImageResource(R.drawable.carted);
        Toast.makeText(OfferView.this, "product added to the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCartItemFailed(Exception e) {

    }

    @Override
    public void onRemoveWishListItemSuccess() {
        wish.setImageResource(R.drawable.wish);
        Toast.makeText(OfferView.this, "product removed from the wishList", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveWishListItemFailed(Exception e) {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
    }
}