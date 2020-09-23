package com.example.elsafa.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elsafa.R;

import java.util.ArrayList;
import java.util.List;

import Model.Category;


public class CategoriesFragment extends Fragment {

    RecyclerView categories_rec_view;
    List<Category> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);

        categories_rec_view = root.findViewById(R.id.categories_rec_view);
        list = new ArrayList<>();

        getCategories();
        return root;
    }

    private void getCategories() {
        Category c = new Category();
        c.setResDrawable(R.drawable.clothing);
        c.setTitle("Clothing");
        list.add(c);
        c.setTitle("Shoes");
        c.setResDrawable(R.drawable.);
    }
}