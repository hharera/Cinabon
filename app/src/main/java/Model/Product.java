package Model;

import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class Product {

    private String title, description, seller;
    private int price, priceOff, wishes, carts;
    private Map<String, String> reviews;
    private List<Blob> photos;

    public Product() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPriceOff() {
        return priceOff;
    }

    public void setPriceOff(int priceOff) {
        this.priceOff = priceOff;
    }

    public int getWishes() {
        return wishes;
    }

    public void setWishes(int wishes) {
        this.wishes = wishes;
    }

    public int getCarts() {
        return carts;
    }

    public void setCarts(int carts) {
        this.carts = carts;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public List<Blob> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Blob> photos) {
        this.photos = photos;
    }
}
