package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

@Entity(value = "suppliers", noClassnameStored=true)
@Indexes(@Index(fields = { @Field("contactName"), @Field("phone") }, options = @IndexOptions(unique = true)))
public class Supplier {

    @Id
    private String supplierId;
    @Property("supplierName")
    private String supplierName;
    @Property("contactName")
    private String contactName; // uniq handle my manwal
    @Property("address")
    private String address;
    @Property("city")
    private String city;
    @Property("postalCode")
    private String postalCode; // uuiq
    @Property("country")
    private String country;
    @Property("phone")
    private String phone; // add the pattern // uniq handle by manwal

    public Supplier() {}

    public Supplier(String supplierName, String contactName, String address,
        String city, String postalCode, String country, String phone) {
        this.supplierName = supplierName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    public Supplier(String supplierId, String supplierName, String contactName,
        String address, String city, String postalCode, String country, String phone) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

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

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
