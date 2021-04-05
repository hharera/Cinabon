package com.whiteside.cafe

import android.content.Intent
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
        fStore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        val animation = AnimationUtils.loadAnimation(this, R.anim.waiting_page_transition)
        val cafe_name = findViewById<TextView?>(R.id.cafe_name)
        cafe_name.startAnimation(animation)
        Handler(mainLooper).postDelayed({ checkInternet() }, 3000)
    }

    private fun initFirebase() {
        if (FirebaseApp.getApps(this).isEmpty()) {
            val options = FirebaseOptions.Builder()
                //old ApplicationId .setApplicationId("1:261802668850:android:3d7d1afc9f8f3d21b0dd2b")
                .setApplicationId("1:261802668850:android:ef229656eca7e9c0b0dd2b")
                .setApiKey("AIzaSyDNwH033gu0gBUBRcINWhNHfbeameUpFFw")
                .setProjectId("ecommerce-55b58")
                .build()
            FirebaseApp.initializeApp(this, options, "Cafe")
        }
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
        auth.signInAnonymously().addOnSuccessListener { addUserToFirebase() }
        val intent = Intent(this, StartingAppPage::class.java)
        startActivity(intent)
        finish()
    }

    private fun addUserToFirebase() {
        auth.uid!!.let {

            val user = User(
                phoneNumber = "NA",
                uid = it,
                cartItems = arrayListOf(),
                wishList = arrayListOf(),
                name = "unknown"
            )

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


                val time = Timestamp.now()
                val offer = Offer(
                    "Meals",
                    endTime = Timestamp(time.seconds + 86400, time.nanoseconds),
                    productId = productsIds[i],
                    discountPercentage = 33f,
                    startTime = time,
                    offerPic = Blob.fromBytes(stream.toByteArray()),
                    type = 1
                )
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                fStore.collection("Offers").document().set(offer)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }
}