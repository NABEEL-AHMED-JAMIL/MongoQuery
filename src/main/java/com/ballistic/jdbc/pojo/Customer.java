package com.ballistic.jdbc.pojo;

public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String phone;

    public Customer() { }

    public Customer(String firstName, String lastName, String city, String country, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Customer{" +
            "id='" + id + '\'' + ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' + ", city='" + city + '\'' +
            ", country='" + country + '\'' + ", phone='" + phone + '\'' +
                '}';
    }
}
