package com.harera.components.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harera.model.modelget.Product
import com.harera.components.product.databinding.CardViewCategoryProductBinding
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private var products: List<Product> = emptyList(),
    private val onProductClicked: (productId: String) -> Unit,
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewCategoryProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProductList(list: List<Product>) {
        products = list
    }

    fun setProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    inner class ViewHolder(val bind: CardViewCategoryProductBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateView(product: Product) {
            Picasso.get().load(product.productPictureUrls[0]).fit().into(bind.image)
            bind.price.text = "${product.price} EGP"
            bind.title.text = product.title

            bind.root.setOnClickListener {
                onProductClicked(
                    product.productId
                )
            }
        }
    }
}