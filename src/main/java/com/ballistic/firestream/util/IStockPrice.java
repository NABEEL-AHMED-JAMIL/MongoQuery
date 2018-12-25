package com.ballistic.firestream.util;

import com.ballistic.firestream.pojo.dto.FieldQuery;

import java.util.ArrayList;
import java.util.List;

public interface IStockPrice {

    public String FIELD_TYPE_STRING = "STRING";
    public String FIELD_TYPE_INTEGER = "INTEGER";
    public String FIELD_TYPE_DOUBLE = "DOUBLE";
    public String FIELD_TYPE_DATE = "DATE";

    public String UUID = "uuid";
    public String DATE = "date";
    public String OPEN_PRICE = "openPrice";
    public String HIGH_PRICE = "highPrice";
    public String LOW_PRICE = "lowPrice";
    public String CLOSE_PRICE = "closePrice";
    public String WAP = "wap";
    public String NO_OF_SHARES = "noOfShares";
    public String NO_OF_TRADES = "noOfTrades";
    public String TOTAL_TURNOVER = "totalTurnover";
    public String DELIVERABLE_QUANTITY = "deliverableQuantity";
    public String DELIQTY_TO_TREADEDQTY = "deliQtyToTradedQty";
    public String SPREAD_HIGH_LOW = "spreadHighLow";
    public String SPREAD_CLOSE_OPEN = "spreadCloseOpen";

    List<KeyValue> STOP_PRICE = new ArrayList<KeyValue>() { {
        add(new KeyValue(UUID, FIELD_TYPE_STRING));
        add(new KeyValue(DATE, FIELD_TYPE_DATE));
        add(new KeyValue(OPEN_PRICE, FIELD_TYPE_DOUBLE));
        add(new KeyValue(HIGH_PRICE, FIELD_TYPE_DOUBLE));
        add(new KeyValue(LOW_PRICE, FIELD_TYPE_DOUBLE));
        add(new KeyValue(CLOSE_PRICE, FIELD_TYPE_DOUBLE));
        add(new KeyValue(WAP, FIELD_TYPE_DOUBLE));
        add(new KeyValue(NO_OF_SHARES, FIELD_TYPE_INTEGER));
        add(new KeyValue(NO_OF_TRADES, FIELD_TYPE_INTEGER));
        add(new KeyValue(TOTAL_TURNOVER, FIELD_TYPE_DOUBLE));
        add(new KeyValue(DELIVERABLE_QUANTITY, FIELD_TYPE_INTEGER));
        add(new KeyValue(DELIQTY_TO_TREADEDQTY, FIELD_TYPE_DOUBLE));
        add(new KeyValue(SPREAD_HIGH_LOW, FIELD_TYPE_DOUBLE));
        add(new KeyValue(SPREAD_CLOSE_OPEN, FIELD_TYPE_DOUBLE));

    }};

    public static Boolean isKeyValueMatch(FieldQuery fieldQuery) {
        Boolean isKeyValueMatch = false;
        if(fieldQuery != null) {
            if(fieldQuery.getField() != null && fieldQuery.getFieldType() != null) {
                KeyValue keyValue = new KeyValue(fieldQuery.getField(), fieldQuery.getFieldType().toUpperCase());
                if(keyValue != null) {
                    isKeyValueMatch = STOP_PRICE.stream().filter(keyValue1 -> {
                        return keyValue1.getKey().equals(keyValue.getKey()) && keyValue1.getValue().equals(keyValue.getValue());
                    }).findFirst().isPresent();
                }
            }
        }
        return isKeyValueMatch;
    }

    public class KeyValue {

        private String key;
        private String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }

        @Override
        public String toString() {
            return "KeyValue{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
        }
    }
}
