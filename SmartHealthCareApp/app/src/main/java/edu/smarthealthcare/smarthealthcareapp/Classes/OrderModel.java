package edu.smarthealthcare.smarthealthcareapp.Classes;

/**
 * Created by RG User on 08/12/17.
 */

public class OrderModel {

    private int OrderId;
    private int CustomerId;
    private  int TotalAmount;
    private int DeliveryStatus;
    private int PackId;

    public OrderModel(int orderId, int customerId, int totalAmount, int deliveryStatus, int packId) {
        OrderId = 0;
        CustomerId = 0;
        TotalAmount = 0;
        DeliveryStatus = 0;
        PackId = 0;
    }

    public int getOrderId() { return OrderId; }

    public void setOrderId(int orderId) { OrderId = orderId; }

    public int getCustomerId() { return CustomerId;}

    public void setCustomerId(int customerId) { CustomerId = customerId; }

    public int getTotalAmount() { return TotalAmount; }

    public void setTotalAmount(int totalAmount) { TotalAmount = totalAmount; }

    public int getDeliveryStatus() { return DeliveryStatus; }

    public void setDeliveryStatus(int deliveryStatus) { DeliveryStatus = deliveryStatus;}

    public int getPackId() { return PackId; }

    public void setPackId(int packId) { PackId = packId; }

}
