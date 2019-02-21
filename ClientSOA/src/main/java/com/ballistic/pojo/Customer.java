package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

@Entity(value = "customers", noClassnameStored=true)
@Indexes(@Index(value = "contactName", fields = @Field("contactName")))
public class Customer {

    @Id
    private String customerId;
    @Property("customerName")
    private String customerName;
    @Property("contactName")
    @Indexed(unique = true)
    private String contactName; //unieq
    @Property("address")
    private String address;
    @Property("city")
    private String city;
    @Property("postalCode")
    private String postalCode;
    @Property("country")
    private String country;

    public Customer() {}

    public Customer(String customerName, String contactName, String address, String city, String postalCode, String country) {
        this.customerName = customerName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public Customer(String customerId, String customerName, String contactName, String address,
        String city, String postalCode, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}
