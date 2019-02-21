package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

@Entity(value = "shippers", noClassnameStored=true)
@Indexes(@Index(fields = { @Field("phone") }, options = @IndexOptions(unique = true)))
public class Shipper {

    @Id
    private String shipperId;
    @Property("shipperName")
    private String shipperName;
    @Property("phone")
    private String phone;

    public Shipper() { }

    public Shipper(String shipperName, String phone) {
        this.shipperName = shipperName;
        this.phone = phone;
    }

    public Shipper(String shipperId, String shipperName, String phone) {
        this.shipperId = shipperId;
        this.shipperName = shipperName;
        this.phone = phone;
    }

    public String getShipperId() { return shipperId; }
    public void setShipperId(String shipperId) { this.shipperId = shipperId; }

    public String getShipperName() { return shipperName; }
    public void setShipperName(String shipperName) { this.shipperName = shipperName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
