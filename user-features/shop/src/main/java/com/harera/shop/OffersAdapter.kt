package com.harera.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.harera.image_slider.databinding.CardViewImageBinding
import com.harera.model.modelget.Offer
import com.squareup.picasso.Picasso

class OffersAdapter(
    private var imageUrls: List<String>,
    findNavController: NavController
) : RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(imageUrls[position % imageUrls.size]).fit().into(holder.bind.image)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    fun setOffers(offers: List<Offer>) {
        imageUrls = offers.map { it.offerImageUrl }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewImageBinding) : RecyclerView.ViewHolder(bind.root) {
        init {
            bind.root.setOnClickListener {}
        }
    }
}
