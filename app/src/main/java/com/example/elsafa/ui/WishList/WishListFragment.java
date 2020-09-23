package com.example.elsafa.ui.WishList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elsafa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class WishListFragment extends Fragment {

    FirebaseAuth auth;
    AdapterViewFlipper view_flipper_offers;
    DatabaseReference dRef;
    StorageReference sRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wishlist, container, false);
        return root;
    }

}