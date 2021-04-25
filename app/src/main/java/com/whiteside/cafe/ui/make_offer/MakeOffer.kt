package com.whiteside.cafe.ui.make_offer

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Timestamp
import com.whiteside.cafe.databinding.ActivityMakeOfferBinding
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.offer.OfferPresenter
import com.whiteside.cafe.utils.BlobBitmap
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MakeOffer : AppCompatActivity() {
    private var offer: Offer = Offer()
    private lateinit var bind: ActivityMakeOfferBinding

    @Inject
    lateinit var offerPresenter: OfferPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMakeOfferBinding.inflate(layoutInflater)
        setContentView(bind.root)

        offer.productId = intent.extras!!.getString("productId")!!
        offer.categoryName = intent.extras!!.getString("categoryName")!!
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            val imageBitmap = BitmapFactory.decodeFile(data.data!!.path)
            offer.offerPic = BlobBitmap.convertBitmapToBlob(imageBitmap)
            bind.image.setImageBitmap(imageBitmap)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun chooseImage(view: View) {
        ImagePicker
            .with(this)
//            .compress(400)
            .start()
    }

    fun saveClicked(view: View) {
//        val calendar: Calendar = GregorianCalendar(bind.time.year, bind.time.month, bind.time.dayOfMonth)

        offer.startTime = Timestamp.now()
        offer.endTime = Timestamp(Timestamp.now().seconds.and(86400), 0)
        offer.discountPercentage = 30.0f
        when (bind.offerType.checkedRadioButtonId) {
            bind.bestOffer.id -> offerPresenter.setBestOffer(offer) {

            }
            bind.lastOffer.id -> offerPresenter.setLastOffer(offer) {

            }
        }
//        offer.discountPercentage = bind.offerDiscount.toString().toFloat()
    }
}