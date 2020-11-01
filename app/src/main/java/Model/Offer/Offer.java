package Model.Offer;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class Offer {

    Timestamp startTime, endTime;
    String offerId;
    Blob offerPic;
    float discountPercentage;

    public Offer() {
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

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Blob getOfferPic() {
        return offerPic;
    }

    public void setOfferPic(Blob offerPic) {
        this.offerPic = offerPic;
    }
}