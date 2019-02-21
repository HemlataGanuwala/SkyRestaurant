package com.flavourheights.apple.skyrestaurantapp.Model;

public class OrderPlanet {

    private String OrderDate;
    private String OrderTime;
    private String NoOfItem;
    private String Amount;

    public OrderPlanet(String orderDate, String orderTime, String amount, String noOfItem)
    {
        this.OrderDate = orderDate;
        this.OrderTime = orderTime;
        this.Amount = amount;
        this.NoOfItem = noOfItem;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getNoOfItem() {
        return NoOfItem;
    }

    public void setNoOfItem(String noOfItem) {
        NoOfItem = noOfItem;
    }
}


