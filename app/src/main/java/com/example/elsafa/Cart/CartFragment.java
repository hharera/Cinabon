package com.example.elsafa.Cart;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import Model.Item;

public class CartFragment extends Fragment implements OnGetCartItem {

    private final FirebaseAuth auth;
    private final FirebaseFirestore fStore;
    private final ArrayList items;
    private CartRecyclerViewAdapter adapter;
    private LinearLayout emptyCart, shopping;
    private View check_out;
    private static TextView totalBillView;
    private double totalBill;
    private CartPresenter presenter;
    private RecyclerView recyclerView;

    public CartFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        items = new ArrayList();
        totalBill = 0;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = root.findViewById(R.id.cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CartRecyclerViewAdapter(items, getContext(), this);
        recyclerView.setAdapter(adapter);

        emptyCart = root.findViewById(R.id.empty_cart);
        shopping = root.findViewById(R.id.shopping);
        check_out = root.findViewById(R.id.check_out);
        totalBillView = root.findViewById(R.id.bill_cost);

        presenter = new CartPresenter();
        presenter.setOnGetCartItem(this);
        presenter.getCartItems();

        return root;
    }


    public void updateView() {
        items.clear();
        adapter.notifyDataSetChanged();
        totalBill = 0;
        presenter.getCartItems();
    }


    public void editTotalBill(double price) {
        totalBill += price;
        totalBillView.setText(totalBill + " EGP");
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

    @Override
    public void onGetCartItemSuccess(Item item) {
        items.add(item);
        adapter.notifyDataSetChanged();
        check_out.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetCartItemFailed(Exception e) {

    }

    @Override
    public void onCartIsEmpty() {
        check_out.setVisibility(View.INVISIBLE);
        getEmptyCartView();
    }
}