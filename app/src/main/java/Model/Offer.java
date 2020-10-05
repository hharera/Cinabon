package Model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Blob;

public class Offer implements Comparable<Offer> {

    private int OID, PID;
    private Timestamp time;
    private Blob offer;
    private float discountValue;

    public Offer() {
    }

    public Blob getOffer() {
        return offer;
    }

    public void setOffer(Blob offer) {
        this.offer = offer;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public void setOID(int OID) {
        this.OID = OID;
    }

    public int getOID() {
        return OID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getPID() {
        return PID;
    }

    @Override
    public int compareTo(Offer o) {
        return (int) (o.getTime().getSeconds() - getTime().getSeconds());
    }
}
