package com.example.elsafa.ui.Cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class CartFragment extends Fragment {

private RecyclerView cartItems;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private Map<String, Integer> cart;
    private Map<String, Integer> offersCart;

    public CartFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        cartItems = root.findViewById(R.id.cart);
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
                        cart = (Map<String, Integer>) ds.get("cart");
                        offersCart = (Map<String, Integer>) ds.get("offersCart");

                        for (String productId : cart.keySet()) {
                            fStore.collection("Products")
                                    .document(productId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot ds) {
                                            Blob pic = (Blob) ds.get("mainPic");

                                        }
                                    });
                        }
                    }
                });
    }
}