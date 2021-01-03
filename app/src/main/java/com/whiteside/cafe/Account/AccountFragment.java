package com.whiteside.cafe.Account;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.whiteside.cafe.CafeLocation;
import com.whiteside.cafe.R;

public class AccountFragment extends Fragment {


    private FrameLayout linear_layout_map;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        TransitionInflater inflater1 = TransitionInflater.from(getContext());
        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));


        linear_layout_map = root.findViewById(R.id.linear_layout_map);

        setListeners();

        return root;
    }

    private void setListeners() {
        setMapListener();
    }

    private void setMapListener() {
        linear_layout_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CafeLocation.class);
                startActivity(intent);
            }
        });
    }
}