package com.ballistic.firestream.util;

import com.ballistic.firestream.pojo.QueryOption;
import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import org.apache.lucene.document.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author :- Nabeel.93
 * @email :- nabeel.amd93@gmail.com
 * Note :- Help this Interface for as Value Converter
 * */
public interface StockPriceUtil extends IStockPrice {

    public final static String FORMAT_DATE = "d-MMMM-yyyy";

    public static Boolean isIdQuery(FieldQuery fieldQuery) {
        Boolean idQuery = false;
        System.out.println("Query Validation :- " + "Field :- (" + fieldQuery.getField() + ") Operation :- (" + QueryOption.getMatchOptionValue(fieldQuery.getOperation()) + ") FieldType :- (" + fieldQuery.getFieldType() + ")");
        if((fieldQuery.getField().equals(UUID) && fieldQuery.getFieldType().equalsIgnoreCase(FIELD_TYPE_STRING)) && fieldQuery.getOperation().equals(QueryOption.EQUAL.getOption())) {
            idQuery = true;
        }
        return idQuery;
    }

    /**
     * Note :- Convert the String of Data to StockPrice
     * Process time is less then 0.ms => done task
     * */
    public static StockPrice rawDataToPojo(String inputLine) {
        long startTime = System.currentTimeMillis();

        StockPrice stockPrice = new StockPrice();
        try {

            String[] stockPrices = inputLine.split(",");
            stockPrice.setUuId(java.util.UUID.randomUUID().toString().replaceAll("-", ""));

            if(stockPrices[0] != null && !stockPrices[0].equals("")) {
                stockPrice.setDate(new SimpleDateFormat(FORMAT_DATE, Locale.ENGLISH).parse(stockPrices[0]));
                System.out.println("Date Found :- " + stockPrice.getDate());
            }
            if(stockPrices[1] != null && !stockPrices[1].equals("")) {
                stockPrice.setOpenPrice(Double.valueOf(stockPrices[1]));
                System.out.println("Open Price Found :- " + stockPrice.getOpenPrice());
            }
            if(stockPrices[2] != null && !stockPrices[2].equals("")) {
                stockPrice.setHighPrice(Double.valueOf(stockPrices[2]));
                System.out.println("Hight Price Found :- " + stockPrice.getHighPrice());
            }
            if(stockPrices[3] != null && !stockPrices[3].equals("")) {
                stockPrice.setLowPrice(Double.valueOf(stockPrices[3]));
                System.out.println("Low Price Found :- " + stockPrice.getLowPrice());
            }
            if(stockPrices[4] != null && !stockPrices[4].equals("")) {
                stockPrice.setClosePrice(Double.valueOf(stockPrices[4]));
                System.out.println("Close Price Found :- " +  stockPrice.getClosePrice());
            }
            if(stockPrices[5] != null && !stockPrices[5].equals("")) {
                stockPrice.setWap(Double.valueOf(stockPrices[5]));
                System.out.println("Wap Found :- " + stockPrice.getWap());
            }
            if(stockPrices[6] != null &&  !stockPrices[6].equals("")) {
                stockPrice.setNoOfShares(Integer.valueOf(stockPrices[6]));
                System.out.println("No of Shares Found :- " + stockPrice.getNoOfShares());
            }
            if(stockPrices[7] != null && !stockPrices[7].equals("")) {
                stockPrice.setNoOfTrades(Integer.valueOf(stockPrices[7]));
                System.out.println("No of Treads Found :- " + stockPrice.getNoOfTrades());
            }
            if(stockPrices[8] != null && !stockPrices[8].equals("")) {
                stockPrice.setTotalTurnover(Double.valueOf(stockPrices[8]));
                System.out.println("Total Turnover Found :- " + stockPrice.getTotalTurnover());
            }
            if(stockPrices[9] != null && !stockPrices[9].equals("")) {
                stockPrice.setDeliverableQuantity(Integer.valueOf(stockPrices[9]));
                System.out.println("Deliverable Quantity :- " + stockPrice.getDeliverableQuantity());
            }
            if(stockPrices[10] != null && !stockPrices[10].equals("")) {
                stockPrice.setDeliQtyToTradedQty(Double.valueOf(stockPrices[10]));
                System.out.println("Deliy Qty To Traded Qty Found :- " + stockPrice.getDeliQtyToTradedQty());
            }
            if(stockPrices[11] != null && !stockPrices[11].equals("")) {
                stockPrice.setSpreadHighLow(Double.valueOf(stockPrices[11]));
                System.out.println("Spread High Low Found :- " + stockPrice.getSpreadHighLow());
            }
            if(stockPrices[12] != null && !stockPrices[12].equals("")) {
                stockPrice.setSpreadCloseOpen(Double.valueOf(stockPrices[12]));
                System.out.println("Spread Close Open Found :- " + stockPrice.getSpreadCloseOpen());
            }

        } catch (ParseException ex) {
            System.err.println("Error " + ex.getLocalizedMessage());
        }  catch (NumberFormatException ex) {
            System.err.println("Error " + ex.getLocalizedMessage());
        }
        System.out.println("Rwa Data   --> StockPrice       Convert Time :- " + (System.currentTimeMillis() - startTime) + ".ms");

        return stockPrice;
    }

    /**
     * Note :- Convert the StockPrice pojo to Lucene Search Document
     * Process time is less then 0.ms => done task
     * */
    public static Document stockPriceToDocument(StockPrice stockPrice) {
        long startTime = System.currentTimeMillis();

        Document document = new Document();
        if(stockPrice.getUuId() != null) {
            document.add(new StringField(UUID, stockPrice.getUuId(),Field.Store.YES));
            System.out.println("UUID Add in Document :- " + stockPrice.getUuId());
        }
        if(stockPrice.getDate() != null) {
            document.add(new StoredField(DATE, stockPrice.getDate().getTime()));
            System.out.println("Date Add in Document :- " + stockPrice.getDate());
        }
        if(stockPrice.getOpenPrice() != null) {
            document.add(new StoredField(OPEN_PRICE, stockPrice.getOpenPrice()));
            System.out.println("Open Price Add in Document :- " + stockPrice.getOpenPrice());
        }
        if(stockPrice.getHighPrice() != null) {
            document.add(new StoredField(HIGH_PRICE, stockPrice.getHighPrice()));
            System.out.println("High Price Add in Document :- " + stockPrice.getHighPrice());
        }
        if(stockPrice.getLowPrice() != null) {
            document.add(new StoredField(LOW_PRICE, stockPrice.getLowPrice()));
            System.out.println("Low Price Add in Document :- " + stockPrice.getLowPrice());
        }
        if(stockPrice.getClosePrice() != null) {
            document.add(new StoredField(CLOSE_PRICE, stockPrice.getClosePrice()));
            System.out.println("Close Price Add in Document :- " + stockPrice.getClosePrice());
        }
        if(stockPrice.getWap() != null) {
            document.add(new StoredField(WAP, stockPrice.getWap()));
            System.out.println("Wap Add in Document :- " + stockPrice.getWap());
        }
        if(stockPrice.getNoOfShares() != null) {
            document.add(new StoredField(NO_OF_SHARES, stockPrice.getNoOfShares()));
            System.out.println("No of Shares Add in Document :- " + stockPrice.getNoOfShares());
        }
        if(stockPrice.getNoOfTrades() != null) {
            document.add(new StoredField(NO_OF_TRADES, stockPrice.getNoOfTrades()));
            System.out.println("No of Trades Add in Document :- " + stockPrice.getNoOfTrades());
        }
        if(stockPrice.getNoOfTrades() != null) {
            document.add(new StoredField(TOTAL_TURNOVER, stockPrice.getTotalTurnover()));
            System.out.println("Total Turnover Add in Document :- " + stockPrice.getNoOfTrades());
        }
        if(stockPrice.getDeliverableQuantity() != null) {
            document.add(new StoredField(DELIVERABLE_QUANTITY, stockPrice.getDeliverableQuantity()));
            System.out.println("Deliverable Quantity Add in Document :- " + stockPrice.getDeliverableQuantity());
        }
        if(stockPrice.getDeliQtyToTradedQty() != null) {
            document.add(new StoredField(DELIQTY_TO_TREADEDQTY, stockPrice.getDeliQtyToTradedQty()));
            System.out.println("Deliqty To Treadqty Add in Document :- " + stockPrice.getDeliQtyToTradedQty());
        }
        if(stockPrice.getSpreadHighLow() != null) {
            document.add(new StoredField(SPREAD_HIGH_LOW, stockPrice.getSpreadHighLow()));
            System.out.println("Spread High Low Add in Document :- " + stockPrice.getSpreadHighLow());
        }
        if(stockPrice.getSpreadCloseOpen() != null) {
            document.add(new StoredField(SPREAD_CLOSE_OPEN, stockPrice.getSpreadCloseOpen()));
            System.out.println("Spread Close Open Add in Document :- " + stockPrice.getSpreadCloseOpen());
        }
        System.out.println("StockPrice --> Lucene(Document) Convert Time :- " + (System.currentTimeMillis() - startTime) + ".ms");

        return document;
    }

    public static StockPrice documentToStockPrice(Document stockPriceDocument) {

        StockPrice stockPrice = new StockPrice();
        if (stockPriceDocument.get(UUID) != null) {
            stockPrice.setUuId(stockPriceDocument.get(UUID));
            System.out.println("UUID PARSE :- " + stockPrice.getUuId());
        }
        if (stockPriceDocument.get(DATE) != null) {
            stockPrice.setDate(new Date(Long.valueOf(stockPriceDocument.get(DATE))));
            System.out.println("DATE PARSE :- " + stockPrice.getDate());
        }
        if(stockPriceDocument.get(OPEN_PRICE) != null) {
            stockPrice.setOpenPrice(Double.valueOf(stockPriceDocument.get(OPEN_PRICE)));
            System.out.println("OPEN_PRICE PARSE :- " + stockPrice.getOpenPrice());
        }
        if(stockPriceDocument.get(HIGH_PRICE) != null) {
            stockPrice.setHighPrice(Double.valueOf(stockPriceDocument.get(HIGH_PRICE)));
            System.out.println("HIGH_PRICE PARSE :- " + stockPrice.getHighPrice());
        }
        if(stockPriceDocument.get(LOW_PRICE) != null) {
            stockPrice.setLowPrice(Double.valueOf(stockPriceDocument.get(LOW_PRICE)));
            System.out.println("LOW_PRICE PARSE :- " + stockPrice.getLowPrice());
        }
        if(stockPriceDocument.get(CLOSE_PRICE) != null) {
            stockPrice.setClosePrice(Double.valueOf(stockPriceDocument.get(CLOSE_PRICE)));
            System.out.println("CLOSE_PRICE PARSE :- " + stockPrice.getClosePrice());
        }
        if(stockPriceDocument.get(WAP) != null) {
            stockPrice.setWap(Double.valueOf(stockPriceDocument.get(WAP)));
            System.out.println("WAP PARSE :- " + stockPrice.getWap());
        }
        if(stockPriceDocument.get(NO_OF_SHARES) != null) {
            stockPrice.setNoOfShares(Integer.valueOf(stockPriceDocument.get(NO_OF_SHARES)));
            System.out.println("NO_OF_SHARES PARSE :- " + stockPrice.getNoOfShares());
        }
        if(stockPriceDocument.get(NO_OF_TRADES) != null) {
            stockPrice.setNoOfTrades(Integer.valueOf(stockPriceDocument.get(NO_OF_TRADES)));
            System.out.println("NO_OF_TRADES PARSE :- " + stockPrice.getNoOfTrades());
        }
        if(stockPriceDocument.get(TOTAL_TURNOVER) != null) {
            stockPrice.setTotalTurnover(Double.valueOf(stockPriceDocument.get(TOTAL_TURNOVER)));
            System.out.println("TOTAL_TURNOVER PARSE :- " + stockPrice.getTotalTurnover());
        }
        if(stockPriceDocument.get(DELIVERABLE_QUANTITY) != null) {
            stockPrice.setDeliverableQuantity(Integer.valueOf(stockPriceDocument.get(DELIVERABLE_QUANTITY)));
            System.out.println("DELIVERABLE_QUANTITY PARSE :- " + stockPrice.getDeliverableQuantity());
        }
        if(stockPriceDocument.get(DELIQTY_TO_TREADEDQTY) != null) {
            stockPrice.setDeliQtyToTradedQty(Double.valueOf(stockPriceDocument.get(DELIQTY_TO_TREADEDQTY)));
            System.out.println("DELIQTY_TO_TREADEDQTY PARSE :- " + stockPrice.getDeliQtyToTradedQty());
        }
        if(stockPriceDocument.get(SPREAD_HIGH_LOW) != null) {
            stockPrice.setSpreadHighLow(Double.valueOf(stockPriceDocument.get(SPREAD_HIGH_LOW)));
            System.out.println("SPREAD_HIGH_LOW PARSE :- " + stockPrice.getSpreadHighLow());
        }
        if(stockPriceDocument.get(SPREAD_CLOSE_OPEN) != null) {
            stockPrice.setSpreadCloseOpen(Double.valueOf(stockPriceDocument.get(SPREAD_CLOSE_OPEN)));
            System.out.println("SPREAD_CLOSE_OPEN PARSE :- " + stockPrice.getSpreadCloseOpen());
        }

        System.out.println("Document ID | " + stockPriceDocument.get(UUID) + " | Date | " + stockPriceDocument.get(DATE)
                + " | OpenPrice | " + stockPriceDocument.get(OPEN_PRICE) + " | HighPrice | " + stockPriceDocument.get(HIGH_PRICE)
                + " | LowPrice | " + stockPriceDocument.get(LOW_PRICE) + " | ClosePrice | " + stockPriceDocument.get(CLOSE_PRICE)
                + " | Wap | " + stockPriceDocument.get(WAP) + " | NoOfShares | " + stockPriceDocument.get(NO_OF_SHARES)
                + " | NoOfTrades | " + stockPriceDocument.get(NO_OF_TRADES) + " | TotalTurnover | " + stockPriceDocument.get(TOTAL_TURNOVER)
                + " | DeliverableQuantity | " + stockPriceDocument.get(DELIVERABLE_QUANTITY) + " | DeliQtyToTradedQty | " + stockPriceDocument.get(DELIQTY_TO_TREADEDQTY)
                + " | SpreadHighLow | " + stockPriceDocument.get(SPREAD_HIGH_LOW) + " | SpreadCloseOpen | " + stockPriceDocument.get(SPREAD_CLOSE_OPEN));
        System.out.println("+------------------------------------------------------------------------------+");


        return stockPrice;
    }

}
