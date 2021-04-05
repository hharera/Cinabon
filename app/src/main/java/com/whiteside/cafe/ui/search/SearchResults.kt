package com.whiteside.cafe.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.CategoryProductsRecyclerViewAdapter
import com.whiteside.cafe.model.Product
import java.util.*

class SearchResults : AppCompatActivity() {
    private lateinit var searchWord: String
    private lateinit var fStore: FirebaseFirestore
    private lateinit var paths: MutableList<String>
    private lateinit var filters: MutableMap<String, Any>
    private lateinit var adapter: CategoryProductsRecyclerViewAdapter
    private lateinit var results: RecyclerView
    private lateinit var products: MutableList<Product>
    private lateinit var searchBar: MaterialSearchBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        fStore = FirebaseFirestore.getInstance()
        paths = ArrayList()
        filters = HashMap()
        products = ArrayList()
        searchWord = intent.extras!!.getString("text")!!
        filters["title"] = searchWord
        searchBar = findViewById(R.id.search_view)
        searchBar.text = searchWord
        results = findViewById(R.id.results)
        results.setHasFixedSize(true)
        results.layoutManager = LinearLayoutManager(this)
        adapter = CategoryProductsRecyclerViewAdapter(products, this)
        results.adapter = adapter
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
                        val title = ds.getString("title")!!
                        if (title.contains(searchWord)) {
                            val product = ds.toObject(Product::class.java)!!
                            product.productId = (ds.id)
                            products.add(product)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
        }
    }

    private fun getProduct(path: String) {
        fStore.document(path)
            .get()
            .addOnSuccessListener { }
    }
}