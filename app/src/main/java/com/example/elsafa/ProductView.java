package com.example.elsafa;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ProductView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
    }

    public void addToWishList(View view) {
    }

    public void addToCart(View view) {

    }

    public void goBack(View view) {
        finish();
    }
}