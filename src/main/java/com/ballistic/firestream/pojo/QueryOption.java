package com.ballistic.firestream.pojo;

import com.google.gson.Gson;

public enum QueryOption {

    EQUAL(0, "="), LESS_THEN(1, "<"), GREATER_THEN(2, ">"),
    LESS_THEN_EQUAL(3, "<="), GREATER_THEN_EQUAL(4, ">="),
    NOT(5, "!"), NOT_EQUAL(6, "!=");

    int option;
    String optionValue;

    QueryOption(int option, String optionValue) {
        this.option = option;
        this.optionValue = optionValue;
    }

    public int getOption() { return option; }
    public String getOptionValue() { return optionValue; }

    public static Boolean idValidOperatorMatch(Integer option) {
        switch (option) {
            case 0: case 1: case 2: case 3: case 4: case 5: case 6:
                return true;
            default:
                return false;
        }
    }

    public static String getMatchOptionValue(Integer option) {
        String optionValue = null;
        switch (option) {
            case 0:
                optionValue = QueryOption.EQUAL.getOptionValue();
                break;
            case 1:
                optionValue = QueryOption.LESS_THEN.getOptionValue();
                break;
            case 2:
                optionValue = QueryOption.GREATER_THEN.getOptionValue();
                break;
            case 3:
                optionValue = QueryOption.LESS_THEN_EQUAL.getOptionValue();
                break;
            case 4:
                optionValue = QueryOption.GREATER_THEN_EQUAL.getOptionValue();
                break;
            case 5:
                optionValue = QueryOption.NOT.getOptionValue();
                break;
            case 6:
                optionValue = QueryOption.NOT_EQUAL.getOptionValue();
                break;
        }
        return optionValue;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
