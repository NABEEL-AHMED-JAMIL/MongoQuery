package com.ballistic.doa.query;

import com.google.gson.Gson;

public class FieldQuery {

    private String field; // field name
    private Integer operation; // (1-6)(=,!=,<,>,<=,>=,!)
    private Object value; // x-y-z

    public FieldQuery() { }

    public FieldQuery(String field, Integer operation, Object value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public Integer getOperation() { return operation; }
    public void setOperation(Integer operation) { this.operation = operation; }

    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }

    @Override
    public String toString() { return new Gson().toJson(this); }

}