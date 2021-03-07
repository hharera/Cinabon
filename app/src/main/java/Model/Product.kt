package Model;

import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class Product {


    private Blob mainPic;
    private List<Blob> productPics;
    private Map<String, Integer> wishes;
    private Map<String, Integer> carts;
    private String productId, categoryName, title;
    private float price;

    public Product() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Blob getMainPic() {
        return mainPic;
    }

    public void setMainPic(Blob mainPic) {
        this.mainPic = mainPic;
    }

    public List<Blob> getProductPics() {
        return productPics;
    }

    public void setProductPics(List<Blob> productPics) {
        this.productPics = productPics;
    }

    public Map<String, Integer> getWishes() {
        return wishes;
    }

    public void setWishes(Map<String, Integer> wishes) {
        this.wishes = wishes;
    }

    public Map<String, Integer> getCarts() {
        return carts;
    }

    public void setCarts(Map<String, Integer> carts) {
        this.carts = carts;
    }
}