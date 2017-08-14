package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 08/14/17.
 */

public class PurchaseHistoryModel {

    private String DrugPackId;
    private String DrugPackName;
    private String TotalAmount;
    private String InvoiceNo;
    private String Date;
    private String kioskId;
    private String Image;

    public String getDrugPackId() {
        return DrugPackId;
    }

    public void setDrugPackId(String drugPackId) {
        DrugPackId = drugPackId;
    }

    public String getDrugPackName() {
        return DrugPackName;
    }

    public void setDrugPackName(String drugPackName) {
        DrugPackName = drugPackName;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getInvoiceNo() {
        return InvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        InvoiceNo = invoiceNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getKioskId() {
        return kioskId;
    }

    public void setKioskId(String kioskId) {
        this.kioskId = kioskId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
