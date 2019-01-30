package com.ballistic.firestream.db;

import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import java.util.List;

public interface IStockPriceDAO {

    // Save
    public void save(List<StockPrice> stockPrices);
    public void save(StockPrice stockPrice);

    // Update
    public void update(List<StockPrice> stockPrices);
    public void update(StockPrice stockPrice);

    // Fetch
    public StockPrice findById(String id);
    public List<StockPrice> getAllStockPrices(Integer pagination);
    public List<StockPrice> getStockPricesByFields(List<FieldQuery> queries);

}
