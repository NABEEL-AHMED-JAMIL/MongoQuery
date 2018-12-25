package com.ballistic.kafka.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.*;

import java.util.Date;

@Entity("photo")
public class Photo {

    @Property("photoName")
    private String photoName;
    @Property("photoUrl")
    private String photoUrl;
    @Property("size")
    private String size;
    @Property("type")
    private String type;
    @Property("created")
    private Date created;
    @Property("update")
    private Date update;

    public Photo() { }

    public Photo(String photoName, String photoUrl, String size, String type, Date created, Date update) {
        this.photoName = photoName;
        this.photoUrl = photoUrl;
        this.size = size;
        this.type = type;
        this.created = created;
        this.update = update;
    }

    public String getPhotoName() { return photoName; }
    public void setPhotoName(String photoName) { this.photoName = photoName; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    public Date getUpdate() { return update; }
    public void setUpdate(Date update) { this.update = update; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
