package Model;

import java.util.Map;

public class User {

    private String UID, name;
    private int phoneNumber;
    private Map<String, Integer> offersCart;
    private Map<String, Integer> productsCart;
    private Map<String, Integer> offersWishList;
    private Map<String, Integer> productsWishList;


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Integer> getOffersCart() {
        return offersCart;
    }

    public void setOffersCart(Map<String, Integer> offersCart) {
        this.offersCart = offersCart;
    }

    public Map<String, Integer> getProductsCart() {
        return productsCart;
    }

    public void setProductsCart(Map<String, Integer> productsCart) {
        this.productsCart = productsCart;
    }

    public Map<String, Integer> getOffersWishList() {
        return offersWishList;
    }

    public void setOffersWishList(Map<String, Integer> offersWishList) {
        this.offersWishList = offersWishList;
    }

    public Map<String, Integer> getProductsWishList() {
        return productsWishList;
    }

    public void setProductsWishList(Map<String, Integer> productsWishList) {
        this.productsWishList = productsWishList;
    }
}
