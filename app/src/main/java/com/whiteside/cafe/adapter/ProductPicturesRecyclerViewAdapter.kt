package com.whiteside.cafe.adapter

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Blob
import com.whiteside.cafe.R

class ProductPicturesRecyclerViewAdapter(
    private val pics: MutableList<Blob>,
    private val context: Context
) : RecyclerView.Adapter<ProductPicturesRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(
            BitmapFactory.decodeByteArray(
                pics.get(position)
                    .toBytes(), 0, pics.get(position).toBytes().size
            )
        )
    }

    override fun getItemCount(): Int {
        return pics.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)

    }
}