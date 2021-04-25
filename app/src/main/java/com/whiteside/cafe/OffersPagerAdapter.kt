package com.whiteside.cafe

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.offer.OfferActivity
import com.whiteside.cafe.utils.BlobBitmap

class OffersPagerAdapter(private val offers: MutableList<Offer>, val type: String) :
    RecyclerView.Adapter<OffersPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateUI(offers[position])
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)

        fun updateUI(offer: Offer) {
            imageView.setImageBitmap(BlobBitmap.convertBlobToBitmap(offer.offerPic))

            imageView.setOnClickListener {
                val intent = Intent(itemView.context, OfferActivity::class.java)
                intent.putExtra("productId", offer.productId)
                intent.putExtra("categoryName", offer.categoryName)
                intent.putExtra("offerId", offer.offerId)
                intent.putExtra("offerType", type)
                itemView.context.startActivity(intent)
            }
        }
    }
}