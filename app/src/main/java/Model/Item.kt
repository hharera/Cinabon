package Model

import com.google.firebase.Timestamp

class Item {
    private var categoryName: String? = null
    private var productId: String? = null
    private var quantity = 0
    private var time: Timestamp? = null
    fun getCategoryName(): String? {
        return categoryName
    }

    fun setCategoryName(categoryName: String?) {
        this.categoryName = categoryName
    }

    fun getProductId(): String? {
        return productId
    }

    fun setProductId(productId: String?) {
        this.productId = productId
    }

    fun getQuantity(): Int {
        return quantity
    }

    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }

    fun getTime(): Timestamp? {
        return time
    }

    fun setTime(time: Timestamp?) {
        this.time = time
    }
}