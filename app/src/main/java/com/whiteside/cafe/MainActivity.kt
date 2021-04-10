package com.whiteside.cafe

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.model.User
import com.whiteside.cafe.utils.Connection
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var fStore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFirebase()

        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val cafeName = findViewById<TextView?>(R.id.cafe_name)
        cafeName.startAnimation(animation)
        Handler(mainLooper).postDelayed({ checkInternet() }, 3000)
    }

    private fun initFirebase() {
        if (FirebaseApp.getApps(applicationContext).isNullOrEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId("1:261802668850:android:c88ecf82d1a11c7bb0dd2b")
                .setApiKey("AIzaSyD067FAA98I17gvHxINUNmjo4GeObS2DSQ")
                .setProjectId("ecommerce-55b58")
                .build()
            FirebaseApp.initializeApp(applicationContext, options, "Cafe")
        }
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    private fun checkInternet() {
        if (Connection.checkConnection(this)) {
            if (auth.currentUser == null) {
                signInAnonymously()
            } else {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            val intent = Intent(this@MainActivity, InternetActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously()
            .addOnSuccessListener { addUserToFirebase() }
            .addOnFailureListener { it.printStackTrace() }
        val intent = Intent(this, StartingAppPage::class.java)
        startActivity(intent)
        finish()
    }

    private fun addUserToFirebase() {
        val user = User()
        user.let {
            it.phoneNumber = "NA"
            it.uid = auth.uid!!
            it.cartItems = arrayListOf()
            it.wishList = arrayListOf()
            it.name = "unknown"
        }

        user.uid.let {
            fStore.collection("Users")
                .document(it)
                .set(user)

            fStore.collection("Users")
                .document(it)
                .collection("Cart")

            fStore.collection("Users")
                .document(it)
                .collection("WishList")
        }
    }

    fun setOffer(i: Int) {
        val imagesURL = arrayOf(
            Uri.parse("https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"),
            Uri.parse("https://addconn.com/images/limited.png")
        )
        val productsIds = arrayOf<String>(
            "xZvcdItmvKWqilQ9ikbm",
            "Lnslv4jK5o7GFrITi5vm"
        )
        Picasso.get().load(imagesURL[i]).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
                val stream = ByteArrayOutputStream()


                val sh: SharedPreferences
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
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                fStore.collection("Offers").document(offer.offerId).set(offer)

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }
}