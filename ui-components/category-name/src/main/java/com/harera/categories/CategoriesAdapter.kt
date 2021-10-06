package com.harera.categories

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.harera.categories.databinding.CardViewCategoriesCategoryBinding
import com.harera.common.utils.navigation.Arguments
import com.harera.common.utils.navigation.Destinations
import com.harera.common.utils.navigation.NavigationUtils
import com.harera.model.modelget.Category

class CategoriesAdapter(
    private var categories: List<Category> = emptyList(),
    private val navController: NavController,
) : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val bind = CardViewCategoriesCategoryBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryName = categories[position].categoryName

        holder.bind.categoryName.text = categoryName
        holder.bind.root.setOnClickListener {
            navController.navigate(
                Uri.parse(
                    NavigationUtils.getUriNavigation(
                        Arguments.HYPER_PANDA_DOMAIN,
                        Destinations.CATEGORIES,
                        categoryName
                    )
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewCategoriesCategoryBinding) :
        RecyclerView.ViewHolder(bind.root)
}