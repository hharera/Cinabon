package com.example.elsafa.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;

import java.util.Arrays;
import java.util.List;

import Controller.CategoriesRecyclerViewAdapter;
import Model.Category;


public class CategoriesFragment extends Fragment {

    RecyclerView recyclerView;
    List<Category> categories;

    private CategoriesRecyclerViewAdapter adapter;

    public CategoriesFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_categories, container, false);

        recyclerView = view.findViewById(R.id.categories_rec_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.scrollBy(0, 50);

        getCategories();

        return view;
    }

    private void getCategories() {
        Category [] categories = new Category[]{
                new Category( "Drinks", R.drawable.drinks),
                new Category( "Meals", R.drawable.meals),
                new Category( "Salads", R.drawable.salads),
                new Category( "Sandwiches", R.drawable.sandwiches),
                new Category( "Sweets", R.drawable.sweets),
                new Category( "Snacks", R.drawable.snacks)
        };

        this.categories = (List<Category>) Arrays.asList(categories);
        adapter = new CategoriesRecyclerViewAdapter(getContext(), this.categories);
        recyclerView.setAdapter(adapter);
    }
}