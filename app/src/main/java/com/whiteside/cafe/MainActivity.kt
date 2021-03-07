package com.whiteside.cafe

import Model.Offer
import Model.User
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
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
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private var fStore: FirebaseFirestore? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFirebase()
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val cafe_name = findViewById<TextView?>(R.id.cafe_name)
        cafe_name.startAnimation(animation)
        Handler().postDelayed({ checkInternet() }, 3000)
    }

    private fun initFirebase() {
        if (FirebaseApp.getApps(this).isEmpty()) {
            val options = FirebaseOptions.Builder()
                .setApplicationId("1:261802668850:android:3d7d1afc9f8f3d21b0dd2b")
                .setApiKey("AIzaSyDNwH033gu0gBUBRcINWhNHfbeameUpFFw")
                .setProjectId("ecommerce-55b58")
                .build()
            FirebaseApp.initializeApp(this, options, "Cafe")
        }
    }

    private fun checkInternet() {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected()
        if (isConnected) {
            if (auth.getCurrentUser() == null) {
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
        auth.signInAnonymously().addOnSuccessListener { addUserToFirebase() }
        val intent = Intent(this, StartingAppPage::class.java)
        startActivity(intent)
        finish()
    }

    private fun addUserToFirebase() {
        val user = User()
        user.name = "NA"
        user.phoneNumber = "NA"
        user.uid = auth.getUid()
        fStore.collection("Users")
            .document(auth.getUid())
            .set(user)
        fStore.collection("Users")
            .document(auth.getUid())
            .collection("Cart")
        fStore.collection("Users")
            .document(auth.getUid())
            .collection("WishList")
    }

    fun setOffer(i: Int) {
        val imagesURL = arrayOf(
            Uri.parse("https://image.shutterstock.com/image-vector/special-offer-banner-vector-format-600w-586903514.jpg"),
            Uri.parse("https://addconn.com/images/limited.png")
        )
        val productsIds = arrayOf<String?>(
            "xZvcdItmvKWqilQ9ikbm",
            "Lnslv4jK5o7GFrITi5vm"
        )
        Picasso.get().load(imagesURL[i]).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: LoadedFrom?) {
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
                fStore.collection("Offers").document().set(offer)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }
}