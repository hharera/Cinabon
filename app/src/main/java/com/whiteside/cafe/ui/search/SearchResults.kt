package com.whiteside.cafe.ui.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.CategoryProductsRecyclerViewAdapter
import com.whiteside.cafe.common.BaseActivity
import com.whiteside.cafe.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchResults : BaseActivity() {
    private lateinit var adapter: CategoryProductsRecyclerViewAdapter
    private lateinit var results: RecyclerView
    private var products = ArrayList<Product>()
    private lateinit var searchBar: MaterialSearchBar

    @Inject
    lateinit var searchResultsPresenter: SearchResultsPresenter

    var searchChannel = Channel<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        val searchWord = intent.extras!!.getString("text")!!
        searchBar = findViewById(R.id.search_view)
        searchBar.text = searchWord
        results = findViewById(R.id.results)
        results.setHasFixedSize(true)
        results.layoutManager = LinearLayoutManager(this)
        adapter = CategoryProductsRecyclerViewAdapter(products)
        results.adapter = adapter

        waitForSearch()
        setupSearchListener()
        GlobalScope.launch {
            searchChannel.send(searchWord)
        }
    }

    private fun setupSearchListener() {
        searchBar.setOnSearchActionListener(object : SimpleOnSearchActionListener() {
            override fun onSearchConfirmed(text: CharSequence?) {
                GlobalScope.launch {
                    searchChannel.send(text.toString())
                }
            }
        })
    }

    private fun waitForSearch() {
        GlobalScope.launch {
            searchChannel.receive().let {
                withContext(Dispatchers.Main) {
                    searchChannel.poll()
                    search(it)
                }
            }
        }
    }

    private fun search(searchWord: String) {
        searchResultsPresenter.searchProduct(searchWord,

            object : SearchListener<Product> {
                override fun onSuccess(result: Product) {
                    handleSuccess()
                    products.add(result)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailed(exception: Exception) {
                    handleFailure(exception)
                }

                override fun onLoading() {
                    products.clear()
                    handleLoading()
                }

                override fun onEmptySearch() {
                    handleSuccess()
                }
            })
    }
}