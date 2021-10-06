package com.harera.shop

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.harera.category_image.databinding.CardViewShopCategoryBinding
import com.harera.common.utils.navigation.Arguments.HYPER_PANDA_DOMAIN
import com.harera.common.utils.navigation.Destinations.CATEGORIES
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.model.modelget.Category
import com.squareup.picasso.Picasso

class CategoriesAdapter(
    private var categories: List<Category> = emptyList(),
    private val navController: NavController,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = CardViewShopCategoryBinding.inflate(LayoutInflater.from(parent.context))
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

    inner class ViewHolder(val bind: CardViewShopCategoryBinding) : RecyclerView.ViewHolder(bind.root) {
        fun updateView(category: Category) {
            bind.categoryTitle.text = category.categoryName
            Picasso.get().load(Uri.parse(category.categoryImagerUrl)).fit().into(bind.categoryImage)

            bind.categoryImage.setOnClickListener {
                val uri = Uri.parse(NavigationUtils.getUriNavigation(HYPER_PANDA_DOMAIN, CATEGORIES, category.categoryName))
                navController.navigate(uri)
            }
        }
    }
}