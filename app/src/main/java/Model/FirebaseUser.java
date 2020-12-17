package Model;

import java.util.List;

public class FirebaseUser extends User {

    private List<Item> cartItems;
    private List<Item> wishList;


    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Item> getWishList() {
        return wishList;
    }

    public void setWishList(List<Item> wishList) {
        this.wishList = wishList;
    }
}
