package com.whiteside.cafe.Category

import Model.Product
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
import com.whiteside.cafe.Product.ProductView
import com.whiteside.cafe.R

class CategoryProductsRecyclerView(
    private val products: MutableList<Product?>?,
    private val context: Context?
) : RecyclerView.Adapter<CategoryProductsRecyclerView.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productMainImage.setImageBitmap(
            BitmapFactory.decodeByteArray(
                products.get(position)
                    .getMainPic().toBytes(), 0, products.get(position).getMainPic().toBytes().size
            )
        )
        holder.price.setText(products.get(position).getPrice().toString() + " EGP")
        holder.title.setText(products.get(position).getTitle())
        holder.productCardView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ProductView::class.java)
            intent.putExtra("productId", products.get(position).getProductId())
            intent.putExtra("categoryName", products.get(position).getCategoryName())
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productMainImage: ImageView?
        private val title: TextView?
        private val price: TextView?
        private val productCardView: CardView?

        init {
            title = itemView.findViewById(R.id.title)
            price = itemView.findViewById(R.id.price)
            productMainImage = itemView.findViewById(R.id.product_main_image)
            productCardView = itemView.findViewById(R.id.category_product_card_view)
        }
    }
}