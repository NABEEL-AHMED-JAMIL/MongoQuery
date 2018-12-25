package com.ballistic.broker;

import com.google.gson.Gson;

/* *
 * Note:- MsgKafka message send-received
 * */
public class MsgKafka {

    private String id;
    private String timestamp;
    // data will be the json-formats send
    private String data;

    public MsgKafka() { }

    public MsgKafka(String id, String timestamp, String data) {
        this.id = id;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}
