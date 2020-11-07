package Model.Product;

import com.google.firebase.firestore.Blob;

public class Product {

    private String productId, categoryName, title;
    private float price;
    private Blob mainPic;

    public Product() {
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
