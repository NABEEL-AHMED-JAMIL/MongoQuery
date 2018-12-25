package com.ballistic.store;

import java.util.Date;
import java.util.List;

public class Bucket {

    private String id;
    private String bucketName;
    private String buketUrl;
    private Date createDate;
    private Date deleteDate;
    private Boolean isDelete = false;
    private List<Store> store;

    public Bucket() {}

    public Bucket(String id, String bucketName, String buketUrl, Date createDate, Date deleteDate, Boolean isDelete) {
        this.id = id;
        this.bucketName = bucketName;
        this.buketUrl = buketUrl;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.isDelete = isDelete;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getBucketName() { return bucketName; }
    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public String getBuketUrl() { return buketUrl; }
    public void setBuketUrl(String buketUrl) { this.buketUrl = buketUrl; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getDeleteDate() { return deleteDate; }
    public void setDeleteDate(Date deleteDate) { this.deleteDate = deleteDate; }

    public Boolean getDelete() { return isDelete; }
    public void setDelete(Boolean delete) { isDelete = delete; }

    public List<Store> getStore() { return store; }
    public void setStore(List<Store> store) { this.store = store; }

    @Override
    public String toString() {
        return "Bucket{" +
                "id='" + id + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", buketUrl='" + buketUrl + '\'' +
                ", createDate=" + createDate +
                ", deleteDate=" + deleteDate +
                ", isDelete=" + isDelete +
                ", store=" + store +
                '}';
    }
}
