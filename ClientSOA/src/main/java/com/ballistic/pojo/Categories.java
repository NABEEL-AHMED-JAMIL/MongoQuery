package com.ballistic.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

@Entity(value = "categories", noClassnameStored=true)
public class Categories {

    @Id
    private String categoryId;
    @Property("categoryName")
    @Indexed(unique = true)
    private String categoryName;
    @Property("description")
    private String description;

    public Categories() {}

    public Categories(String categoryName, String description) {
        this.categoryName = categoryName.trim();
        this.description = description.trim();
    }

    public Categories(String categoryId, String categoryName, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName.trim(); }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description.trim(); }

    @Override
    public String toString() { return new Gson().toJson(this); }

}
