package Model.Product;

import com.google.firebase.firestore.Blob;

public class Product {

    private String productId, category, price, title;
    private Blob mainPic;

    public Product() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blob getMainPic() {
        return mainPic;
    }

    public void setMainPic(Blob mainPic) {
        this.mainPic = mainPic;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
