package com.harera.shop

import android.net.Uri
import android.os.Build.PRODUCT
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.harera.common.utils.navigation.Arguments.HYPER_PANDA_DOMAIN
import com.harera.common.utils.navigation.NavigationUtils.getUriNavigation
import com.harera.model.modelget.Product
import com.harera.shop.databinding.CardViewShopProductBinding
import com.squareup.picasso.Picasso

class ProductsAdapter(
    private var products: List<Product> = emptyList(),
    private val navController: NavController,
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CardViewShopProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class ViewHolder(val bind: CardViewShopProductBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun updateView(product: Product) {
            Picasso.get().load(product.productPictureUrls[0]).fit().into(bind.productImage)
            bind.price.text = " ح.م${product.price}"
            bind.title.text = product.title

            bind.root.setOnClickListener {
                navController.navigate(
                    Uri.parse(getUriNavigation(HYPER_PANDA_DOMAIN, PRODUCT, product.productId)),
                )
            }
        }
    }
}