package com.ballistic.firestream.util;

import com.ballistic.firestream.pojo.QueryOption;
import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Note :- Query Builder Interface help to create query related to StockPrice pojo
 * */
public interface DbQueryBuilder extends IStockPrice {

    /**
     * Note :- Filter Query
     * */
    public static void getQuery(FieldQuery fieldQuery, Query query) throws Exception {
        Boolean isKeyValueMatch = IStockPrice.isKeyValueMatch(fieldQuery);
        if(isKeyValueMatch) {
            Integer operation = fieldQuery.getOperation();
            if(operation != null && QueryOption.idValidOperatorMatch(fieldQuery.getOperation())) {
                switch (operation) {
                    case 0: // EQUAL
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 1: // LESS_THEN
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 2: // GREATER_THEN
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 3: // LESS_THEN_EQUAL
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 4: // GREATER_THEN_EQUAL
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 5: // NOT
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                    case 6: // NOT_EQUAL
                        query.filter(fieldQuery.getField() + " " + QueryOption.getMatchOptionValue(fieldQuery.getOperation()), fieldQuery.getValue());
                        break;
                }
            }else {
                System.err.println("Warn :- " + "Operation :- " + fieldQuery.getOperation());
            }
        }else {
            throw new Exception("Error :- " + "Field :- " + fieldQuery.getField() + " FieldType :- " + fieldQuery.getFieldType());
        }
    }

    /***
     * Note :- Query Update use for update the value of StockPrice
     */
    public static void updateQueryStockPrice(UpdateOperations<StockPrice> updateOps, StockPrice stockPrice) {
        long startTime = System.currentTimeMillis();

        if(stockPrice.getDate() != null) {
            updateOps.set(DATE, stockPrice.getDate());
            System.out.println("StockPrice Update Date :-" + stockPrice.getDate());
        }
        if(stockPrice.getOpenPrice() != null) {
            updateOps.set(OPEN_PRICE, stockPrice.getOpenPrice());
            System.out.println("StockPrice Update OpenPrice :-" + stockPrice.getOpenPrice());
        }
        if(stockPrice.getHighPrice() != null) {
            updateOps.set(HIGH_PRICE, stockPrice.getHighPrice());
            System.out.println("StockPrice Update HighPrice :-" + stockPrice.getHighPrice());
        }
        if(stockPrice.getLowPrice() != null) {
            updateOps.set(LOW_PRICE, stockPrice.getLowPrice());
            System.out.println("StockPrice Update LowPrice :-" + stockPrice.getLowPrice());
        }
        if(stockPrice.getClosePrice() != null) {
            updateOps.set(CLOSE_PRICE, stockPrice.getClosePrice());
            System.out.println("StockPrice Update ClosePrice :-" + stockPrice.getClosePrice());
        }
        if(stockPrice.getWap() != null) {
            updateOps.set(WAP, stockPrice.getWap());
            System.out.println("StockPrice Update Wap :-" + stockPrice.getWap());
        }
        if(stockPrice.getNoOfShares() != null) {
            updateOps.set(NO_OF_SHARES, stockPrice.getNoOfShares());
            System.out.println("StockPrice Update Wap :-" + stockPrice.getWap());
        }
        if(stockPrice.getNoOfTrades() != null) {
            updateOps.set(NO_OF_TRADES, stockPrice.getNoOfTrades());
            System.out.println("StockPrice Update NoOfTrades :-" + stockPrice.getNoOfTrades());
        }
        if(stockPrice.getTotalTurnover() != null) {
            updateOps.set(TOTAL_TURNOVER, stockPrice.getTotalTurnover());
            System.out.println("StockPrice Update TotalTurnover :-" + stockPrice.getTotalTurnover());
        }
        if(stockPrice.getDeliverableQuantity() != null) {
            updateOps.set(DELIVERABLE_QUANTITY, stockPrice.getDeliverableQuantity());
            System.out.println("StockPrice Update DeliverableQuantity :-" + stockPrice.getDeliverableQuantity());
        }
        if(stockPrice.getDeliQtyToTradedQty() != null) {
            updateOps.set(DELIQTY_TO_TREADEDQTY, stockPrice.getDeliQtyToTradedQty());
            System.out.println("StockPrice Update DeliQtyToTradedQty :-" + stockPrice.getDeliQtyToTradedQty());
        }
        if(stockPrice.getSpreadHighLow() != null) {
            updateOps.set(SPREAD_HIGH_LOW, stockPrice.getSpreadHighLow());
            System.out.println("StockPrice Update SpreadHighLow :-" + stockPrice.getSpreadHighLow());
        }
        if(stockPrice.getClosePrice() != null) {
            updateOps.set(SPREAD_CLOSE_OPEN, stockPrice.getSpreadCloseOpen());
            System.out.println("StockPrice Update SpreadCloseOpen :-" + stockPrice.getSpreadCloseOpen());
        }
        System.out.println("StockPrice --> Lucene(Document) Convert Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

}
