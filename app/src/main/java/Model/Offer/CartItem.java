package Model.Offer;


import com.google.firebase.firestore.Blob;

public class CartItem {

    private String title, offerId;
    private float price;
    private Blob offerPic;


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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Blob getOfferPic() {
        return offerPic;
    }

    public void setOfferPic(Blob offerPic) {
        this.offerPic = offerPic;
    }
}
