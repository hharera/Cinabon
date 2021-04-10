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
import androidx.databinding.DataBindingUtil
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
import com.whiteside.cafe.databinding.ActivityHomeBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.search.SearchResults
import java.io.ByteArrayOutputStream


class HomeActivity : AppCompatActivity() {
    private var fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bind: ActivityHomeBinding

    private var searchBar: MaterialSearchBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(bind.toolBar)

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
    }

    private fun setUpWithDrawer() {
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setUpWithBottomNav() {
        bind.bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }

    private fun setUpWithSideNav() {
        bind.navView?.let {
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
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val time = Timestamp.now()
                val offer = Offer()
                offer.let {
                    it.productId = productsIds[i]
                    it.discountPercentage = 33f
                    it.startTime = time
                    it.endTime = Timestamp(time.seconds + 86400, time.nanoseconds)
                    it.categoryName = "Meals"
                    it.offerPic = Blob.fromBytes(stream.toByteArray())
                    it.type = 1
                    it.offerId = fStore.collection("Offers").document().id
                }

                fStore = FirebaseFirestore.getInstance()
                fStore.collection("Offers").document(offer.offerId).set(offer)
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        })
    }
}