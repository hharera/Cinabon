package com.whiteside.cafe.ui.category

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.FirebaseFirestore
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.CategoryProductsRecyclerViewAdapter
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.product.OnGetProductListener
import com.whiteside.cafe.ui.search.SearchResults
import java.io.ByteArrayOutputStream
import java.util.*

class CategoryProducts : AppCompatActivity(), OnGetProductListener {
    private lateinit var categoryName: String
    private lateinit var itemList: MutableList<Product>
    private lateinit var fStore: FirebaseFirestore
    private lateinit var adapter: CategoryProductsRecyclerViewAdapter
    private lateinit var searchBar: MaterialSearchBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        searchBar = findViewById(R.id.search_view)
        fStore = FirebaseFirestore.getInstance()
        categoryName = intent.extras!!.getString("category")!!
        val recyclerView = findViewById<RecyclerView?>(R.id.products)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemList = ArrayList()
        adapter = CategoryProductsRecyclerViewAdapter(itemList, this)
        recyclerView.adapter = adapter
        val presenter = CategoryProductsPresenter(this)
        presenter.getProducts(categoryName)
        setSearchListeners()
    }

    private fun setSearchListeners() {
        searchBar.setOnSearchActionListener(object : SimpleOnSearchActionListener() {
            override fun onSearchConfirmed(text: CharSequence?) {
                val intent = Intent(this@CategoryProducts, SearchResults::class.java)
                intent.putExtra("text", text.toString())
                startActivity(intent)
            }
        })
    }

    private fun setOffer(i: Int) {
        val imagesURL = arrayOf<String?>(
            "https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg",
            "https://addconn.com/images/limited.png",
            "http://www.cullinsyard.co.uk/wp-content/uploads/2018/01/special-offer.jpg"
        )
        Picasso.get().load(imagesURL[i]).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom?) {
                val time = Timestamp.now()
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val blobs: MutableList<Blob> = ArrayList()
                blobs.add(Blob.fromBytes(stream.toByteArray()))


                val productId = fStore.collection("Categories")
                    .document(categoryName)
                    .collection("Products")
                    .document()
                    .id

                val product = Product(
                    title = "Buy 2 cups of Black Coffee and take the third as a gift",
                    carts = HashMap(),
                    wishes = HashMap(),
                    categoryName = categoryName,
                    price = 20f,
                    productPics = blobs,
                    mainPic = Blob.fromBytes(stream.toByteArray()),
                    productId = productId
                )

                fStore.collection("Categories")
                    .document(categoryName)
                    .collection("Products")
                    .document(product.productId)
                    .set(product)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }

    override fun onGetProductSuccess(product: Product) {
        itemList.add(product)
        adapter.notifyDataSetChanged()
    }

    override fun onGetProductFailed(e: Exception) {}

    companion object {
        private val TAG: String? = "CategoryProducts"
    }
}