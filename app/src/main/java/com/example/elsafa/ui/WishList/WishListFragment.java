package com.example.elsafa.ui.WishList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import Controller.WishListRecyclerViewAdapter;
import Model.Item;

public class WishListFragment extends Fragment {

    private RecyclerView recyclerView;
    private FirebaseAuth auth;
    private FirebaseFirestore fStore;
    private List<Item> wishListItems;
    private WishListRecyclerViewAdapter adapter;

    public WishListFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        wishListItems = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);

        recyclerView = root.findViewById(R.id.wish_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new WishListRecyclerViewAdapter(wishListItems, getContext());
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        getWishListItems();
    }

    private void getWishListItems() {
        fStore.collection("Users")
                .document(auth.getUid())
                .collection("WishList")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()) {
                            wishListItems.add(ds.toObject(Item.class));
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}