package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 09/12/17.
 */

public class ExpiryModel {

    private String drug_name;
    private String expiry_date;
    private String days_remaining;
    private String barcode;

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDays_remaining() {
        return days_remaining;
    }

    public void setDays_remaining(String days_remaining) {
        this.days_remaining = days_remaining;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
