package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "product", noClassnameStored = true)
public class Product {

    @Id
    private String productId;
    @Property("productName")
    private String productName;
    @Property("unit")
    private String unit;
    @Property("price")
    private Double price;
    @Property("supplierId")
    private String supplierId;
    @Property("categoriesId")
    private String categoriesId;

    public Product() {}

    public Product(String productName, String unit, Double price, String supplierId, String categoriesId) {
        this.productName = productName;
        this.unit = unit;
        this.price = price;
        this.supplierId = supplierId;
        this.categoriesId = categoriesId;
    }

    public Product(String productId, String productName, String unit, Double price, String supplierId, String categoriesId) {
        this.productId = productId;
        this.productName = productName;
        this.unit = unit;
        this.price = price;
        this.supplierId = supplierId;
        this.categoriesId = categoriesId;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getCategoriesId() { return categoriesId; }
    public void setCategoriesId(String categoriesId) { this.categoriesId = categoriesId; }

    @Override
    public String toString() { return new Gson().toJson(this); }
}
