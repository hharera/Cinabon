package com.example.elsafa;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import Model.Item;

public class CategoryProducts extends AppCompatActivity {

    private static final String TAG = "CategoryProducts";
    private String category;
    private List<Item> itemList;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        fStore = FirebaseFirestore.getInstance();
        category = getIntent().getExtras().getString("category");

        getItems();
    }

    private void getItems() {
        fStore.collection("Categories")
                .document(category)
                .collection("Items")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            for (DocumentSnapshot ds : value.getDocuments()) {
                                Item item = ds.toObject(Item.class);
                                itemList.add(item);
                            }

                        }
                    }
                });
    }
}