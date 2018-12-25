package com.ballistic.kafka.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity("device")
@Indexes(@Index(fields = { @Field("devName") }, options = @IndexOptions(unique = true)))
public class Device {

    @Id
    private String id;
    @Property("devName")
    private String devName;
    @Property("price")
    private String price;
    @Property("parchesDate")
    private Date parchesDate;
    @Property("saleDate")
    private Date saleDate;
    @Property("status")
    private Status status;
    @Embedded
    private Photo photo;

    public Device() { }

    public Device(String id, String devName, String price) {
        this.id = id;
        this.devName = devName;
        this.price = price;
    }

    public Device(String devName, String price, Date parchesDate, Date saleDate, Status status) {
        this.devName = devName;
        this.price = price;
        this.parchesDate = parchesDate;
        this.saleDate = saleDate;
        this.status = status;
    }

    public Device(String id, String devName, String price, Date parchesDate, Status status, Photo photo) {
        this.id = id;
        this.devName = devName;
        this.price = price;
        this.parchesDate = parchesDate;
        this.status = status;
        this.photo = photo;
    }

    public Device(String id, String devName, String price, Date parchesDate, Date saleDate, Status status) {
        this.id = id;
        this.devName = devName;
        this.price = price;
        this.parchesDate = parchesDate;
        this.saleDate = saleDate;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDevName() { return devName; }
    public void setDevName(String devName) { this.devName = devName; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public Date getParchesDate() { return parchesDate; }
    public void setParchesDate(Date parchesDate) { this.parchesDate = parchesDate; }

    public Date getSaleDate() { return saleDate; }
    public void setSaleDate(Date saleDate) { this.saleDate = saleDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public Photo getPhoto() { return photo; }
    public void setPhoto(Photo photo) { this.photo = photo; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
