package Model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class User {

    private String UID, name;
    private int phoneNumber;


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



}
