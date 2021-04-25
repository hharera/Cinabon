package com.whiteside.cafe.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.whiteside.cafe.databinding.ProductCardViewBinding
import com.whiteside.cafe.model.Product
import com.whiteside.cafe.ui.product.ProductActivity
import com.whiteside.cafe.utils.BlobBitmap

class CategoryProductsRecyclerViewAdapter(
    private val products: ArrayList<Product>
) :
    RecyclerView.Adapter<CategoryProductsRecyclerViewAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ProductCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let {
                return ViewHolder(it)
            }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(val bind: ProductCardViewBinding) : RecyclerView.ViewHolder(bind.root) {
        fun updateView(product: Product) {
            bind.productMainImage.setImageBitmap(BlobBitmap.convertBlobToBitmap(product.productPics[0]))
            bind.price.text = "${product.price} EGP"
            bind.title.text = product.title

            bind.root.setOnClickListener {
                val intent = Intent(bind.root.context, ProductActivity::class.java)
                intent.putExtra("productId", product.productId)
                intent.putExtra("categoryName", product.categoryName)
                bind.root.context.startActivity(intent)
            }
        }
    }
}