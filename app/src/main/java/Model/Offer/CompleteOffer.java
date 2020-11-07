package Model.Offer;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class CompleteOffer {

    private String title, offerId;
    private Timestamp startTime, endTime;
    private Blob offerPic;
    private List<Blob> productPics;
    private float discountPercentage, price;
    private Map<String, Integer> wishes;
    private Map<String, Integer> carts;

    public CompleteOffer() {
    }

    public Map<String, Integer> getCarts() {
        return carts;
    }

    public void setCarts(Map<String, Integer> carts) {
        this.carts = carts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Map<String, Integer> getWishes() {
        return wishes;
    }

    public void setWishes(Map<String, Integer> wishes) {
        this.wishes = wishes;
    }
}
