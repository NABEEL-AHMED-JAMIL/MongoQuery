package com.ballistic.store;

import java.util.Date;
import java.util.Set;

public class Store {

    private String id;
    private String name;
    private String folder; // folder-case
    private Double size; // size
    private String type;
    private Date createDate;
    private Date updateDate;
    private Date deleteDate;
    private Boolean isDelete = false;
    private Set<String> downloadUrl;
    private Set<Store> storeList; // in case folder
}
