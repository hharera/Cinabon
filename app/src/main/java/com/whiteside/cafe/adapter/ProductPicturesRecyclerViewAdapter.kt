package com.whiteside.cafe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Blob
import com.whiteside.cafe.R
import com.whiteside.cafe.utils.BlobBitmap

class ProductPicturesRecyclerViewAdapter(private val pics: MutableList<Blob>) :
    RecyclerView.Adapter<ProductPicturesRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.offer_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageBitmap(BlobBitmap.convertBlobToBitmap(pics[position]))
    }

    override fun getItemCount(): Int {
        return pics.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.item_image)
    }
}