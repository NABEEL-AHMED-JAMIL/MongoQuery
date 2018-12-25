package com.ballistic.firestream.db;

import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;

import java.util.List;

public class StockPriceManager {

    private IStockPriceDAO stockPriceDAO;

    public StockPriceManager(IStockPriceDAO stockPriceDAO) {
        System.out.println("StockPriceManager:- Constructor...Init");
        this.stockPriceDAO = stockPriceDAO;
        System.out.println("StockPriceManager:-  Constructor...End");
    }

    public void save(List<StockPrice> stockPrices) { this.stockPriceDAO.save(stockPrices); }

    public void save(StockPrice stockPrice) { this.stockPriceDAO.save(stockPrice); }

    public void update(List<StockPrice> stockPrices) { this.stockPriceDAO.update(stockPrices); }

    public void update(StockPrice stockPrice) { this.stockPriceDAO.update(stockPrice); }

    public StockPrice findById(String id) { return this.stockPriceDAO.findById(id); }

    public List<StockPrice> getAllStockPrices(Integer pagination) { return this.stockPriceDAO.getAllStockPrices(pagination); }

    public List<StockPrice> getStockPricesByFields(List<FieldQuery> queries) { return this.stockPriceDAO.getStockPricesByFields(queries); }

}
