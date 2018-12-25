package com.ballistic.kafka.pojo;

import com.google.gson.Gson;

public enum  Status {

    NEW("New"), SECOND("Second");

    private String	value;

    Status(String value) { this.value = value; }

    public String getValue( ) { return value; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}
