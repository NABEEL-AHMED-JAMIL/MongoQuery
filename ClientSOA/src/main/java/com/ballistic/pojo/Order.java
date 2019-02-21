package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.List;

@Entity(value = "orders", noClassnameStored=true)
public class Order {

    @Id
    private String orderId;
    @Property("customerId")
    private String customerId;
    @Property("employeeId")
    private String employeeId;
    @Property("orderDate")
    private Date orderDate;
    @Property("shipperId")
    private String shipperId;
    @Reference
    @Property("orderDetailIds")
    private List<String> orderDetailIds;

    public Order() {}

    public Order(String orderId, String customerId, String employeeId, Date orderDate, String shipperId, List<String> orderDetailIds) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.orderDate = orderDate;
        this.shipperId = shipperId;
        this.orderDetailIds = orderDetailIds;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getShipperId() { return shipperId; }
    public void setShipperId(String shipperId) { this.shipperId = shipperId; }

    public List<String> getOrderDetailIds() { return orderDetailIds; }
    public void setOrderDetailIds(List<String> orderDetailIds) { this.orderDetailIds = orderDetailIds; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}
