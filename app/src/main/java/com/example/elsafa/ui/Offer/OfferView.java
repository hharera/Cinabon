package com.example.elsafa.ui.Offer;

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
import com.example.elsafa.Cart.OnCartChangedListener;
import com.example.elsafa.R;
import com.example.elsafa.ui.Product.OnGetProductListener;
import com.example.elsafa.ui.Product.ProductPresenter;
import com.example.elsafa.ui.shop.OnGetOffersListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import Controller.ProductPicturesRecyclerViewAdapter;
import Controller.WishList;
import Model.Offer.Offer;
import Model.Product.CompleteProduct;

public class OfferView extends AppCompatActivity implements OnGetOffersListener, OnGetProductListener, OnCartChangedListener {

    private FirebaseAuth auth;

    private ImageView wish, cart, back;
    private TextView title, endTime, price;
    private ViewPager2 productPics;
    private String productId;
    private FirebaseFirestore fStore;
    private CompleteProduct product;
    private Offer offer;
    private String categoryName;
    private String offerId;
    private OfferPresenter offerPresenter;
    private ProductPresenter productPresenter;
    private CartPresenter cartPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_view);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        productId = getIntent().getExtras().getString("productId");
        categoryName = getIntent().getExtras().getString("categoryName");
        offerId = getIntent().getExtras().getString("offerId");

        wish = findViewById(R.id.wish);
        back = findViewById(R.id.back);
        cart = findViewById(R.id.cart);
        title = findViewById(R.id.title);
        productPics = findViewById(R.id.product_pics);
        endTime = findViewById(R.id.end_Time);
        price = findViewById(R.id.price);

        productPresenter = new ProductPresenter(this);
        cartPresenter = new CartPresenter(this);

        offerPresenter = new OfferPresenter(this);
        offerPresenter.getOffer(offerId);
    }

    public void cartClicked(View view) {
        Map<String, Integer> carts = product.getCarts();

        if (carts.containsKey(auth.getUid())) {
            cartPresenter.removeItem(product);
        } else {
            cartPresenter.addItem(product);
        }
    }

    public void wishClicked(View view) {
        WishList wishList = new WishList();
        if (product.getWishes().containsKey(auth.getUid())) {
            if (wishList.removeItem(product)) {
                Toast.makeText(OfferView.this, "Offer Added to the wishList", Toast.LENGTH_SHORT).show();
                wish.setImageResource(R.drawable.wish);
            }
        } else {
            if (wishList.addItem(product)) {
                wish.setImageResource(R.drawable.wished);
                Toast.makeText(OfferView.this, "product removed from the wishList", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goBack(View view) {
        finish();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(Offer offer) {
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
    public void onFailed(Exception e) {

    }

    @Override
    public void onGetProductSuccess(CompleteProduct product) {
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
    }

    @Override
    public void onGetProductFailed(Exception e) {

    }

    @Override
    public void onRemoveItemSuccess() {
        this.cart.setImageResource(R.drawable.cart);
        Toast.makeText(this, "Product removed from the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddItemSuccess() {
        this.cart.setImageResource(R.drawable.carted);
        Toast.makeText(this, "Product Added to the cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveItemFailed() {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddItemFailed() {
        Toast.makeText(this, "Operation Failed", Toast.LENGTH_SHORT).show();
    }
}