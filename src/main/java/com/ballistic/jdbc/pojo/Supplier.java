package com.ballistic.jdbc.pojo;

public class Supplier {

    private String id;
    private String companyName;
    private String contactName;
    private String city;
    private String country;
    private String phone;
    private String fax;

    public Supplier() {}

    public Supplier(String companyName, String contactName, String city, String country, String phone, String fax) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' + ", companyName='" + companyName + '\'' +
                ", contactName='" + contactName + '\'' + ", city='" + city + '\'' +
                ", country='" + country + '\'' + ", phone='" + phone + '\'' +
                ", fax='" + fax + '\'' + '}';
    }
}
