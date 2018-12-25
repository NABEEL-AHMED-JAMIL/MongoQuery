package com.ballistic.jdbc.pojo;

import java.util.Date;

public class Order {

    private String id;
    private Date orderDate;
    private String customerId;
    private Double totalAmount;

    public Order() { }

    public Order(Date orderDate, String customerId, Double totalAmount) {
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' + ", orderDate=" + orderDate + ", customerId='" + customerId + '\'' +
                ", totalAmount=" + totalAmount + '}';
    }
}
