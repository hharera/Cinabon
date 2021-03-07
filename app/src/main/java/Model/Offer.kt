package Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

public class Offer implements Comparable<Offer> {

    private String categoryName, productId, offerId;
    private Timestamp startTime, endTime;
    private Blob offerPic;
    private float discountPercentage;
    public int type;

    public Offer() {
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Blob getOfferPic() {
        return offerPic;
    }

    public void setOfferPic(Blob offerPic) {
        this.offerPic = offerPic;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    @Override
    public int compareTo(Offer o) {
        if (type == 1) {
            return (int) (o.getDiscountPercentage() - this.getDiscountPercentage());
        } else {
            return o.getStartTime().compareTo(this.getStartTime());
        }
    }
}



