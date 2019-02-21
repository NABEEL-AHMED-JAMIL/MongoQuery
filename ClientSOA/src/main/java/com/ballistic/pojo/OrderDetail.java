package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "orderDetails", noClassnameStored=true)
public class OrderDetail {

    @Id
    private String orderDetailId;
    @Property("productId")
    private String productId;
    @Property("quantity")
    private Integer quantity;
    @Property("orderId")
    private String orderId;

    public OrderDetail() {}

    public OrderDetail(String productId, Integer quantity, String orderId) {
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public String getOrderDetailId() { return orderDetailId; }
    public void setOrderDetailId(String orderDetailId) { this.orderDetailId = orderDetailId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
