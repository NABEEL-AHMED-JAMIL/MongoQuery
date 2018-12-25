package com.ballistic.jdbc.pojo;

public class Product {

    private String id;
    private String productName;
    private String supplierid;
    private String unitPrice;
    private String _package;
    private String isDiscontinued;

    public Product() { }

    public Product(String productName, String supplierid, String unitPrice, String _package, String isDiscontinued) {
        this.productName = productName;
        this.supplierid = supplierid;
        this.unitPrice = unitPrice;
        this._package = _package;
        this.isDiscontinued = isDiscontinued;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String get_package() {
        return _package;
    }

    public void set_package(String _package) {
        this._package = _package;
    }

    public String getIsDiscontinued() {
        return isDiscontinued;
    }

    public void setIsDiscontinued(String isDiscontinued) {
        this.isDiscontinued = isDiscontinued;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' + ", productName='" + productName + '\'' + ", supplierid='" + supplierid + '\'' +
                ", unitPrice='" + unitPrice + '\'' + ", _package='" + _package + '\'' +
                ", isDiscontinued='" + isDiscontinued + '\'' + '}';
    }
}
