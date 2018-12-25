package com.ballistic.firestream.pojo;

import com.google.gson.Gson;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

@Entity
public class StockPrice {

    @Id
    @Property("uuId")
    private String uuId;
    private Date date;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double closePrice;
    private Double wap;
    private Integer noOfShares;
    private Integer noOfTrades;
    private Double totalTurnover;
    private Integer deliverableQuantity;
    private Double deliQtyToTradedQty;
    private Double spreadHighLow;
    private Double spreadCloseOpen;

    public StockPrice() {}

    public StockPrice(Date date, Double openPrice, Double highPrice, Double lowPrice, Double closePrice, Double wap,
         Integer noOfShares, Integer noOfTrades, Double totalTurnover, Integer deliverableQuantity,
         Double deliQtyToTradedQty, Double spreadHighLow, Double spreadCloseOpen) {
        this.date = date;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.closePrice = closePrice;
        this.wap = wap;
        this.noOfShares = noOfShares;
        this.noOfTrades = noOfTrades;
        this.totalTurnover = totalTurnover;
        this.deliverableQuantity = deliverableQuantity;
        this.deliQtyToTradedQty = deliQtyToTradedQty;
        this.spreadHighLow = spreadHighLow;
        this.spreadCloseOpen = spreadCloseOpen;
    }

    public String getUuId() { return uuId; }
    public void setUuId(String uuId) { this.uuId = uuId; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Double getOpenPrice() { return openPrice; }
    public void setOpenPrice(Double openPrice) { this.openPrice = openPrice; }

    public Double getHighPrice() { return highPrice; }
    public void setHighPrice(Double highPrice) { this.highPrice = highPrice; }

    public Double getLowPrice() { return lowPrice; }
    public void setLowPrice(Double lowPrice) { this.lowPrice = lowPrice; }

    public Double getClosePrice() { return closePrice; }
    public void setClosePrice(Double closePrice) { this.closePrice = closePrice; }

    public Double getWap() { return wap; }
    public void setWap(Double wap) { this.wap = wap; }

    public Integer getNoOfShares() { return noOfShares; }
    public void setNoOfShares(Integer noOfShares) { this.noOfShares = noOfShares; }

    public Integer getNoOfTrades() { return noOfTrades; }
    public void setNoOfTrades(Integer noOfTrades) { this.noOfTrades = noOfTrades; }

    public Double getTotalTurnover() { return totalTurnover; }
    public void setTotalTurnover(Double totalTurnover) { this.totalTurnover = totalTurnover; }

    public Integer getDeliverableQuantity() { return deliverableQuantity; }
    public void setDeliverableQuantity(Integer deliverableQuantity) { this.deliverableQuantity = deliverableQuantity; }

    public Double getDeliQtyToTradedQty() { return deliQtyToTradedQty; }
    public void setDeliQtyToTradedQty(Double deliQtyToTradedQty) { this.deliQtyToTradedQty = deliQtyToTradedQty; }

    public Double getSpreadHighLow() { return spreadHighLow; }
    public void setSpreadHighLow(Double spreadHighLow) { this.spreadHighLow = spreadHighLow; }

    public Double getSpreadCloseOpen() { return spreadCloseOpen; }
    public void setSpreadCloseOpen(Double spreadCloseOpen) { this.spreadCloseOpen = spreadCloseOpen; }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
