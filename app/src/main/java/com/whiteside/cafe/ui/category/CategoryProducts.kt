package com.whiteside.cafe.ui.category

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.whiteside.cafe.R
import com.whiteside.cafe.adapter.CategoryProductsRecyclerViewAdapter
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.search.SearchResults
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CategoryProducts : AppCompatActivity() {
    private lateinit var categoryName: String
    private var productList: ArrayList<Product> = ArrayList()
    private var fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var adapter: CategoryProductsRecyclerViewAdapter
    private lateinit var searchBar: MaterialSearchBar

    @Inject
    lateinit var presenter: CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)

        searchBar = findViewById(R.id.search_view)
        categoryName = intent.extras!!.getString("category")!!

        val recyclerView = findViewById<RecyclerView>(R.id.products)
        adapter = CategoryProductsRecyclerViewAdapter(productList, this)
        recyclerView.adapter = adapter

        presenter.getCategoryProducts(categoryName) {
            productList.add(it)
            adapter.notifyDataSetChanged()
        }
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

//    private fun setOffer(i: Int) {
//        val imagesURL = arrayOf<String?>(
//            "https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg",
//            "https://addconn.com/images/limited.png",
//            "http://www.cullinsyard.co.uk/wp-content/uploads/2018/01/special-offer.jpg"
//        )
//        Picasso.get().load(imagesURL[i]).into(object : Target {
//            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom?) {
//                val time = Timestamp.now()
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val blobs: ArrayList<Blob> = ArrayList()
//                blobs.add(Blob.fromBytes(stream.toByteArray()))
//
//
//                val productId = fStore.collection("Categories")
//                    .document(categoryName)
//                    .collection("Products")
//                    .document()
//                    .id
//
//                val product = Product()
//                product.let {
//                    it.title = "Buy 2 cups of Black Coffee and take the third as a gift"
//                    it.carts = HashMap()
//                    it.wishes = HashMap()
//                    it.categoryName = categoryName
//                    it.price = 20f
//                    it.productPics = blobs
////                    it.mainPic = Blob.fromBytes(stream.toByteArray())
//                    it.productId = productId
//                }
//
//                fStore.collection("Categories")
//                    .document(categoryName)
//                    .collection("Products")
//                    .document(product.productId)
//                    .set(product)
//            }
//
//            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
//        })
//    }
}