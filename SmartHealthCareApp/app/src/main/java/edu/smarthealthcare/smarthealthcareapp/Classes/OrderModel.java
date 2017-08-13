package edu.smarthealthcare.smarthealthcareapp.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RG User on 08/12/17.
 */

public class OrderModel {

    @SerializedName("OrderId")
    @Expose
    private String OrderId;
    @SerializedName("CustomerId")
    @Expose
    private String CustomerId;
    @SerializedName("TotalAmount")
    @Expose
    private  String TotalAmount;
    @SerializedName("DeliveryStatus")
    @Expose
    private String DeliveryStatus;
    @SerializedName("PackId")
    @Expose
    private String PackId;
    @SerializedName("AddedDate")
    @Expose
    private String AddedDate;
    @SerializedName("DrugPackName")
    @Expose
    private String DrugPackName;
    @SerializedName("Image")
    @Expose
    private String Image;


    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        DeliveryStatus = deliveryStatus;
    }

    public String getPackId() {
        return PackId;
    }

    public void setPackId(String packId) {
        PackId = packId;
    }

    public String getAddedDate() {
        return AddedDate;
    }

    public void setAddedDate(String addedDate) {
        AddedDate = addedDate;
    }

    public String getDrugPackName() {
        return DrugPackName;
    }

    public void setDrugPackName(String drugPackName) {
        DrugPackName = drugPackName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
