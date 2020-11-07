package Model;

import com.google.firebase.Timestamp;

import io.grpc.EquivalentAddressGroup;

public class WishListItem {


    private String categoryName, productId;
    private Timestamp time;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
