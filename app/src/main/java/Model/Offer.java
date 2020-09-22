package Model;

import android.graphics.Bitmap;

import java.util.Date;

public class Offer {

    private int OID, PID;
    private Date endDate;
    private Bitmap bitmap;

    public Offer() {
    }

    public void setOID(int OID) {
        this.OID = OID;
    }

    public int getOID() {
        return OID;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPID() {
        return PID;
    }

    public Date getEndDate() {
        return endDate;
    }
}
