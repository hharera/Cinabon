package com.whiteside.cafe

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.*
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.FirebaseFirestore
import com.mancj.materialsearchbar.MaterialSearchBar
import com.mancj.materialsearchbar.SimpleOnSearchActionListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.search.SearchResults
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_wishlist.*
import java.io.ByteArrayOutputStream


class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var searchBar: MaterialSearchBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(tool_bar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shop,
                R.id.navigation_categories,
                R.id.navigation_account,
                R.id.navigation_cart,
                R.id.navigation_wishlist,
                R.id.navigation_search
            )
        )

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            setUpWithDrawer()
        else
            setUpWithToolBar()

        setUpWithBottomNav()
        setUpWithSideNav()
        setShoppingListener()
    }

    private fun setUpWithDrawer() {
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setUpWithBottomNav() {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpWithSideNav() {
        nav_view?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpWithToolBar() {
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.tool_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun setShoppingListener() {
        cart_shopping?.setOnClickListener {
            navController.navigate(R.id.cart_shop_action)
        }
        wishlist_shopping?.setOnClickListener {
            navController.navigate(R.id.wishlist_shop_action)
        }
    }

    private fun serSearchListeners() {
        searchBar!!.setOnSearchActionListener(object : SimpleOnSearchActionListener() {
            override fun onSearchConfirmed(text: CharSequence) {
                super.onSearchConfirmed(text)
                val intent = Intent(this@HomeActivity, SearchResults::class.java)
                intent.putExtra("text", text.toString())
                startActivity(intent)
            }
        })
    }

    private fun setOffer(i: Int) {
        val imagesURL = arrayOf(
            Uri.parse("https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"),
            Uri.parse("https://addconn.com/images/limited.png")
        )
        val productsIds = arrayOf(
            "xZvcdItmvKWqilQ9ikbm",
            "Lnslv4jK5o7GFrITi5vm"
        )
        Picasso.get().load(imagesURL[i]).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                val time = Timestamp.now()
                val offer = Offer()
                offer.productId = productsIds[i]
                offer.discountPercentage = 33f
                offer.startTime = time
                offer.endTime = Timestamp(time.seconds + 86400, time.nanoseconds)
                offer.categoryName = "Meals"
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                offer.offerPic = Blob.fromBytes(stream.toByteArray())
                val fStore = FirebaseFirestore.getInstance()
                fStore.collection("Offers").document().set(offer)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
    }
}