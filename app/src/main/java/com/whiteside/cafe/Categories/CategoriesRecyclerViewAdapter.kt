package com.whiteside.cafe.Categories

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.Category.CategoryProducts
import com.whiteside.cafe.R

class CategoriesRecyclerViewAdapter(private val context: Context?) :
    RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder?>() {
    private val categoryName: Array<String?>?
    private val categoryDrawable: TypedArray?
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.category_title.setText(categoryName.get(position))
        holder.category_img.setImageResource(categoryDrawable.getResourceId(position, 0))
        holder.category_img.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CategoryProducts::class.java)
            intent.putExtra("category", categoryName.get(position))
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return categoryName.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_title: TextView?
        var category_img: ImageView?

        init {
            category_title = itemView.findViewById(R.id.category_title)
            category_img = itemView.findViewById(R.id.category_img)
        }
    }

    init {
        categoryName = context.getResources().getStringArray(R.array.categories)
        categoryDrawable = context.getResources().obtainTypedArray(R.array.categories_drawables)
    }
}