package com.ballistic.firestream.pojo.dto;

import com.google.gson.Gson;

public class FieldQuery {

    private String field;
    private Integer operation;
    private Object value;
    private String fieldType;

    public FieldQuery() { }

    public FieldQuery(String field, Integer operation, Object value, String fieldType) {
        this.field = field;
        this.operation = operation;
        this.value = value;
        this.fieldType = fieldType;
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public Integer getOperation() { return operation; }
    public void setOperation(Integer operation) { this.operation = operation; }

    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }

    public String getFieldType() { return fieldType; }
    public void setFieldType(String fieldType) { this.fieldType = fieldType; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
