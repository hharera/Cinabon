package Model.Product;

import com.google.firebase.firestore.Blob;

import java.util.List;
import java.util.Map;

public class CompleteProduct extends Product{

    private Blob mainPic;
    private List<Blob> productPics;
    private Map<String, String> wishes;
    private Map<String, String> carts;

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
