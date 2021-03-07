package com.whiteside.cafe.Search;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.SimpleOnSearchActionListener;
import com.whiteside.cafe.Category.CategoryProductsRecyclerView;
import com.whiteside.cafe.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Product;


public class SearchResults extends AppCompatActivity {


    private String searchWord;
    private FirebaseFirestore fStore;
    private List<String> paths;
    private Map<String, Object> filters;
    private CategoryProductsRecyclerView adapter;
    private RecyclerView results;
    private List<Product> products;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        fStore = FirebaseFirestore.getInstance();
        paths = new ArrayList<>();
        filters = new HashMap<>();
        products = new ArrayList<>();

        searchWord = getIntent().getExtras().getString("text");
        filters.put("title", searchWord);

        searchBar = findViewById(R.id.search_view);
        searchBar.setText(searchWord);

        results = findViewById(R.id.results);
        results.setHasFixedSize(true);
        results.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CategoryProductsRecyclerView(products, this);
        results.setAdapter(adapter);

        getResults();
        setSearchBarListeners();
    }

    private void setSearchBarListeners() {
        searchBar.setOnSearchActionListener(new SimpleOnSearchActionListener() {
            @Override
            public void onSearchConfirmed(CharSequence text) {
                searchWord = String.valueOf(text);
                products.clear();
                adapter.notifyDataSetChanged();
                getResults();
            }
        });
    }

    private void getResults() {
        for (String categoryName : getResources().getStringArray(R.array.categories)) {
            fStore.collection("Categories")
                    .document(categoryName)
                    .collection("Products")
                    .whereLessThanOrEqualTo("title", searchWord)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot querySnapshot) {
                            for (DocumentSnapshot ds : querySnapshot.getDocuments()) {
                                String title = ds.getString("title");
                                if (title.contains(searchWord)) {
                                    Product product = ds.toObject(Product.class);
                                    product.setProductId(ds.getId());
                                    products.add(product);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }

    private void getProduct(String path) {
        fStore.document(path)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot ds) {

                    }
                });
    }
}