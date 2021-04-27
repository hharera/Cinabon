package com.whiteside.cafe.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.R
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.utils.BlobBitmap

class OffersPagerAdapter(
    private val offers: MutableList<Offer>,
    var navController: NavController,
    val type: String,
) :
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
                val bundle = Bundle()
                bundle.putString("offerId", offer.offerId)
                bundle.putString("offerType", type)
                navController.navigate(R.id.view_offer, bundle)
//                val intent = Intent(itemView.context, OfferActivity::class.java)
//                itemView.context.startActivity(intent)
            }
        }
    }
}