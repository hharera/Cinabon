package com.example.elsafa.ui.Cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.HomeActivity;
import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import Controller.CartRecyclerViewAdapter;
import Model.Item;

public class CartFragment extends Fragment {

    private final FirebaseAuth auth;
    private final FirebaseFirestore fStore;
    private final ArrayList items;
    private CartRecyclerViewAdapter adapter;
    private LinearLayout emptyCart, shopping;
    private View check_out;
    private TextView totalBillView;
    private double totalBill;

    public CartFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        items = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CartRecyclerViewAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

//        TransitionInflater inflater1 = TransitionInflater.from(getContext());
//        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
//        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));

        emptyCart = root.findViewById(R.id.empty_cart);
        shopping = root.findViewById(R.id.shopping);
        check_out = root.findViewById(R.id.check_out);
        totalBillView = root.findViewById(R.id.bill_cost);

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
                .collection("Cart")
                .get()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                            Item item = ds.toObject(Item.class);
                            items.add(item);
                            getPrice(item);
                            adapter.notifyDataSetChanged();
                        }

                        if (queryDocumentSnapshots.isEmpty()) {
                            getEmptyCartView();
                        } else {
                            check_out.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void getPrice(Item item) {
        fStore.collection("Categories")
                .document(item.getCategoryName())
                .collection("Products")
                .document(item.getProductId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {
                        totalBill+= ds.getDouble("price");
                        totalBillView.setText(String.valueOf(totalBill) + " EGP");
                    }
                });
    }

    private void getEmptyCartView() {
        emptyCart.setVisibility(View.VISIBLE);
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

    }
}