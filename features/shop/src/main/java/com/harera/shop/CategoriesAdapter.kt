package com.harera.shop

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.repository.domain.Category
import com.harera.shop.databinding.CardViewCategoryBinding
import com.squareup.picasso.Picasso

class CategoriesAdapter(
    private var categories: List<Category> = emptyList(),
    private val onCategoryClicked: (String) -> Unit,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardViewCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewCategoryBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun updateView(category: Category) {
            bind.categoryTitle.text = category.categoryName
            Picasso.get().load(Uri.parse(category.categoryImagerUrl)).fit().into(bind.categoryImage)

            bind.categoryImage.setOnClickListener {
                onCategoryClicked(category.categoryName)
            }
        }
    }
}