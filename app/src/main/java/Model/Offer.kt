package Model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Blob

class Offer : Comparable<Offer?> {
    private var categoryName: String? = null
    private var productId: String? = null
    private var offerId: String? = null
    private var startTime: Timestamp? = null
    private var endTime: Timestamp? = null
    private var offerPic: Blob? = null
    private var discountPercentage = 0f
    var type = 0
    fun getOfferId(): String? {
        return offerId
    }

    fun setOfferId(offerId: String?) {
        this.offerId = offerId
    }

    fun getStartTime(): Timestamp? {
        return startTime
    }

    fun setStartTime(startTime: Timestamp?) {
        this.startTime = startTime
    }

    fun getEndTime(): Timestamp? {
        return endTime
    }

    fun setEndTime(endTime: Timestamp?) {
        this.endTime = endTime
    }

    fun getOfferPic(): Blob? {
        return offerPic
    }

    fun setOfferPic(offerPic: Blob?) {
        this.offerPic = offerPic
    }

    fun getDiscountPercentage(): Float {
        return discountPercentage
    }

    fun setDiscountPercentage(discountPercentage: Float) {
        this.discountPercentage = discountPercentage
    }

    fun getProductId(): String? {
        return productId
    }

    fun setProductId(productId: String?) {
        this.productId = productId
    }

    fun getCategoryName(): String? {
        return categoryName
    }

    fun setCategoryName(categoryName: String?) {
        this.categoryName = categoryName
    }

    override fun compareTo(o: Offer?): Int {
        return if (type == 1) {
            (o.getDiscountPercentage() - getDiscountPercentage()) as Int
        } else {
            o.getStartTime().compareTo(getStartTime())
        }
    }
}