package edu.smarthealthcare.smarthealthcareapp.Classes;

import java.util.List;

/**
 * Created by RG User on 08/20/17.
 */

public class KioskModel {
    private String KioskId;
    private String Location;
    private String Address;
    private List<FirstAidKitModel> FirstAidKitModel;

    public String getKioskId() {
        return KioskId;
    }

    public void setKioskId(String kioskId) {
        KioskId = kioskId;
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

    public List<edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel> getFirstAidKitModel() {
        return FirstAidKitModel;
    }

    public void setFirstAidKitModel(List<edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel> firstAidKitModel) {
        FirstAidKitModel = firstAidKitModel;
    }
}
