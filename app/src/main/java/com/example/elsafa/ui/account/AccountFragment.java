package com.example.elsafa.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.elsafa.CafeLocation;
import com.example.elsafa.R;

public class AccountFragment extends Fragment {


    private FrameLayout linear_layout_map;
    private RadioButton black_theme;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);

        TransitionInflater inflater1 = TransitionInflater.from(getContext());
        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));


        black_theme = root.findViewById(R.id.black_theme);
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