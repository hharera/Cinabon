package com.example.elsafa.ui.WishList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.HomeActivity;
import com.example.elsafa.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    private LinearLayout empty_wish_list, shopping;
    private View root;

    public WishListFragment() {
        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        wishListItems = new ArrayList();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_wishlist, container, false);

        empty_wish_list = root.findViewById(R.id.empty_wish_list);
        shopping = root.findViewById(R.id.shopping);

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
                        if (queryDocumentSnapshots.isEmpty()) {
                            setEmptyView();

                        }
                    }
                });
    }

    private void setEmptyView() {
        recyclerView.setVisibility(View.INVISIBLE);
        empty_wish_list.setVisibility(View.VISIBLE);

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
//                BottomNavigationView view1 = getView().findViewById(R.id.nav_view);
//                view1.setSelectedItemId(R.id.navigation_categories);
            }
        });

    }

}