package com.whiteside.cafe

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.model.Offer
import com.whiteside.cafe.ui.offer.OfferView

class OffersPagerAdapter(private val offers: MutableList<Offer>, private val context: Context) :
    RecyclerView.Adapter<OffersPagerAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(
            BitmapFactory.decodeByteArray(
                offers[position].offerPic.toBytes(), 0, offers.get(position).offerPic.toBytes().size
            )
        )
        holder.imageView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, OfferView::class.java)
            intent.putExtra("productId", offers[position].productId)
            intent.putExtra("categoryName", offers[position].categoryName)
            intent.putExtra("offerId", offers[position].offerId)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)
    }
}