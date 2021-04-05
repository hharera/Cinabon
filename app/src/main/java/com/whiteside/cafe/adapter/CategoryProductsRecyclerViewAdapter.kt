package com.whiteside.cafe.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.R
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.product.ProductView

class CategoryProductsRecyclerViewAdapter(
    private val products: MutableList<Product>,
    private val context: Context
) : RecyclerView.Adapter<CategoryProductsRecyclerViewAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productMainImage.setImageBitmap(
            BitmapFactory.decodeByteArray(
                products[position].productPics[0].toBytes(),
                0,
                products.get(position).productPics[0].toBytes().size
            )
        )
        holder.price.text = products[position].price.toString() + " EGP"
        holder.title.text = products[position].price.toString()
        holder.productCardView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ProductView::class.java)
            intent.putExtra("productId", products.get(position).productId)
            intent.putExtra("categoryName", products.get(position).categoryName)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productMainImage: ImageView
        val title: TextView
        val price: TextView
        val productCardView: CardView

        init {
            title = itemView.findViewById(R.id.title)
            price = itemView.findViewById(R.id.price)
            productMainImage = itemView.findViewById(R.id.product_main_image)
            productCardView = itemView.findViewById(R.id.category_product_card_view)
        }
    }
}