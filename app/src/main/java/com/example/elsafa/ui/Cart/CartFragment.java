package com.example.elsafa.ui.Cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import Controller.CartItemsRecyclerViewAdapter;

public class CartFragment extends Fragment {

    private RecyclerView cartItems;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Map<String, Integer> products;
    private Map<String, Integer> offers;
    private CartItemsRecyclerViewAdapter adapter;

    public CartFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        offers = new HashMap<String, Integer>();
        products = new HashMap<>();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        cartItems = root.findViewById(R.id.cart);
        cartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        cartItems.setHasFixedSize(true);
        adapter = new CartItemsRecyclerViewAdapter(offers, products, getContext());
        cartItems.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getCartItems();
    }

    private void getCartItems() {
        fStore.collection("Users")
                .document(auth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        offers = (Map<String, Integer>) ds.get("offersCart");
                        adapter.setOffers(offers);
                    }
                });

        fStore.collection("Users")
                .document(auth.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        products = (Map<String, Integer>) ds.get("productsCart");
                        adapter.setProducts(products);
                    }
                });
    }
}