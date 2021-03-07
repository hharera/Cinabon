package com.whiteside.cafe.Search

import Model.Product
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.whiteside.cafe.Category.CategoryProductsRecyclerView
import com.whiteside.cafe.R
import java.util.*

class SearchResults : AppCompatActivity() {
    private var searchWord: String? = null
    private var fStore: FirebaseFirestore? = null
    private var paths: MutableList<String?>? = null
    private var filters: MutableMap<String?, Any?>? = null
    private var adapter: CategoryProductsRecyclerView? = null
    private var results: RecyclerView? = null
    private var products: MutableList<Product?>? = null
    private var searchBar: MaterialSearchBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        fStore = FirebaseFirestore.getInstance()
        paths = ArrayList()
        filters = HashMap()
        products = ArrayList()
        searchWord = intent.extras.getString("text")
        filters["title"] = searchWord
        searchBar = findViewById(R.id.search_view)
        searchBar.setText(searchWord)
        results = findViewById(R.id.results)
        results.setHasFixedSize(true)
        results.setLayoutManager(LinearLayoutManager(this))
        adapter = CategoryProductsRecyclerView(products, this)
        results.setAdapter(adapter)
        getResults()
        setSearchBarListeners()
    }

    private fun setSearchBarListeners() {
        searchBar.setOnSearchActionListener(object : SimpleOnSearchActionListener() {
            override fun onSearchConfirmed(text: CharSequence?) {
                searchWord = text.toString()
                products.clear()
                adapter.notifyDataSetChanged()
                getResults()
            }
        })
    }

    private fun getResults() {
        for (categoryName in resources.getStringArray(R.array.categories)) {
            fStore.collection("Categories")
                .document(categoryName)
                .collection("Products")
                .whereLessThanOrEqualTo("title", searchWord)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (ds in querySnapshot.documents) {
                        val title = ds.getString("title")
                        if (title.contains(searchWord)) {
                            val product = ds.toObject(Product::class.java)
                            product.setProductId(ds.id)
                            products.add(product)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
        }
    }

    private fun getProduct(path: String?) {
        fStore.document(path)
            .get()
            .addOnSuccessListener { }
    }
}