package com.example.elsafa.Categories;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;


public class CategoriesFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.categories_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CategoriesRecyclerViewAdapter adapter = new CategoriesRecyclerViewAdapter(getContext());
        recyclerView.setAdapter(adapter);

        TransitionInflater inflater1 = TransitionInflater.from(getContext());
        setExitTransition(inflater1.inflateTransition(R.transition.fragment_in));
        setEnterTransition(inflater1.inflateTransition(R.transition.fragment_out));

        return view;
    }
}