package Model.Offer;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class CompleteOffer {

    String desc, offerId;
    Timestamp startTime, endTime;
    Blob offerPic;
    List<Blob> productPics;
    float discountPercentage, price;
    Map<String, String> wishes;
    Map<String, String> carts;

    public CompleteOffer() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public List<Blob> getProductPics() {
        return productPics;
    }

    public void setProductPics(List<Blob> productPics) {
        this.productPics = productPics;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Map<String, String> getWishes() {
        return wishes;
    }

    public void setWishes(Map<String, String> wishes) {
        this.wishes = wishes;
    }

    public Map<String, String> getCarts() {
        return carts;
    }

    public void setCarts(Map<String, String> carts) {
        this.carts = carts;
    }
}
