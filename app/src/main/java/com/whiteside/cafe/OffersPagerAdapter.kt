package com.whiteside.cafe

import Model.Offer
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.Offer.OfferView

class OffersPagerAdapter(private val offers: MutableList<Offer?>?, private val context: Context?) :
    RecyclerView.Adapter<OffersPagerAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(
            BitmapFactory.decodeByteArray(
                offers.get(position)
                    .getOfferPic().toBytes(), 0, offers.get(position).getOfferPic().toBytes().size
            )
        )
        holder.imageView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, OfferView::class.java)
            intent.putExtra("productId", offers.get(position).getProductId())
            intent.putExtra("categoryName", offers.get(position).getCategoryName())
            intent.putExtra("offerId", offers.get(position).getOfferId())
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView?

        init {
            imageView = itemView.findViewById(R.id.item_image)
        }
    }
}