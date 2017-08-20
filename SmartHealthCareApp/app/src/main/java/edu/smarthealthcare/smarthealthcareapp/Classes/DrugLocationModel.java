package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 08/20/17.
 */

public class DrugLocationModel {

    private String KioskId;
    private String AvailQuantity;
    private  String Location;
    private String Address;

    public String getKioskId() {
        return KioskId;
    }

    public void setKioskId(String kioskId) {
        KioskId = kioskId;
    }

    public String getAvailQuantity() {
        return AvailQuantity;
    }

    public void setAvailQuantity(String availQuantity) {
        AvailQuantity = availQuantity;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}