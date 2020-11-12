package Model.Product;

import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class CompleteProduct extends Product {

    private Blob mainPic;
    private List<Blob> productPics;
    private Map<String, Integer> wishes;
    private Map<String, Integer> carts;

    public CompleteProduct() {
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