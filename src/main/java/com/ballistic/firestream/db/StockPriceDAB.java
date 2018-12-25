package com.ballistic.firestream.db;

import com.ballistic.firestream.pojo.dto.FieldQuery;
import com.ballistic.firestream.util.IStockPrice;
import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.util.DbQueryBuilder;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.mongodb.MongoException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Note :- StockPrice help work like Middle wire b/w entity and database
 * */
public class StockPriceDAB implements IStockPriceDAO, IStockPrice {

    private DbConnection dbConnection;

    private static Cache<String, List<StockPrice>> fetchCacheStockPrice =
            CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(1, TimeUnit.MINUTES).build();

    public StockPriceDAB(DbConnection dbConnection) throws IOException { this.dbConnection = dbConnection; }

    /**
     * Note :- Store Multiple Data at once
     * */
    @Override
    public void save(List<StockPrice> stockPrices) {
        long startTime = System.currentTimeMillis();
        this.dbConnection.getDb().save(stockPrices);
        System.out.println("Db Store Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    /**
     * Note:- Use for Single Data at once
     * */
    @Override
    public void save(StockPrice stockPrice) {
        long startTime = System.currentTimeMillis();
        this.dbConnection.getDb().save(stockPrice);
        System.out.println("Db Store Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    /**
     * Note :- Update Method use for Update multiple data at once
     * */
    @Override
    public void update(List<StockPrice> stockPrices) {
        long startTime = System.currentTimeMillis();
        if(!stockPrices.equals(null) && stockPrices.size() > 0) {
            // update process will handle with single update bz it's risky process
            stockPrices.parallelStream().forEach(stockPrice -> { update(stockPrice); });
        } else {
            System.err.println("StockPrice's have Null Object So Update Process Fail");
        }
        System.out.println("Db Update Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    /**
     * Note :- Update for Single Date at once
     * */
    @Override
    public void update(StockPrice stockPrice) {
        long startTime = System.currentTimeMillis();
        if(!stockPrice.equals(null)) {
            if(stockPrice.getUuId() != null) {
                try {
                    final Query<StockPrice> findQuery = this.dbConnection.getDb()
                        .createQuery(StockPrice.class).field(UUID).equal(stockPrice.getUuId()).disableValidation();
                    UpdateOperations<StockPrice> updateOps = this.dbConnection.getDb().createUpdateOperations(StockPrice.class);
                    DbQueryBuilder.updateQueryStockPrice(updateOps, stockPrice);
                    this.dbConnection.getDb().update(findQuery, updateOps, true);
                } catch (MongoException ex) {
                    System.err.println("Error " + ex.getLocalizedMessage());
                } catch (NumberFormatException ex) {
                    System.err.println("Error " + ex.getLocalizedMessage());
                } catch (Exception ex) {
                    System.err.println("Error " + ex.getLocalizedMessage());
                }
            } else {
                System.err.println("Zero Document fetch..");
            }
        } else {
            System.err.println("StockPrice have Null Object So Update Process Fail");
        }
        System.out.println("Db Update Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    /**
     * Note :- Fetch data by ID
     * */
    @Override
    public StockPrice findById(String id) {
        long startTime = System.currentTimeMillis();
        StockPrice stockPrice = new StockPrice();
        if(id != null) {
            stockPrice = this.dbConnection.getDb().createQuery(StockPrice.class).field(UUID).equal(id).get();
        }
        System.out.println("Db Fetch Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return stockPrice;
    }

    /**
     * Note :- Update for Single Date at once
     * */
    @Deprecated
    @Override
    public List<StockPrice> getAllStockPrices(Integer pagination) {
        long startTime = System.currentTimeMillis();
        List<StockPrice> stockPrices = Lists.newArrayList();
        try {
            stockPrices = this.dbConnection.getDb().find(StockPrice.class).limit(pagination).asList();
            System.out.println("Db Fetch Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return stockPrices;
        }catch (OutOfMemoryError e) {
            System.err.println("Error " + e.getLocalizedMessage());
            return stockPrices;
        }
    }

    /**
     * Note :- Search data related to the stock-prices
     * */
    @Override
    public List<StockPrice> getStockPricesByFields(List<FieldQuery> fieldQuerys) throws NullPointerException {
        long startTime = System.currentTimeMillis();
        Query<StockPrice> query = this.dbConnection.getDb().createQuery(StockPrice.class);
        List<StockPrice> stockPrices = new ArrayList<>();
        if(fieldQuerys != null) {
            try {
                fieldQuerys.stream().forEach(fieldQuery -> {
                    try {
                        DbQueryBuilder.getQuery(fieldQuery, query);
                    } catch (Exception ex) {
                        throw new NullPointerException("Error :- " + ex.getLocalizedMessage());
                    }
                });
                // fire-process will be done here..
                stockPrices = fetchCacheStockPrice.get(query.toString(), new Callable<List<StockPrice>>() {
                    @Override
                    public List<StockPrice> call() throws Exception {
                        List<StockPrice> stockPrices = query.asList();
                        System.out.println("Query :- " +  query.toString() + " Total Result " + stockPrices.size());
                        return stockPrices;
                    }
                });
            } catch (ExecutionException ex) {
                System.err.println("Error  :- " + ex.getLocalizedMessage());
            }
            System.out.println("Fetch Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
            return stockPrices;
        } else {
            throw new NullPointerException("Error :- Query Null");
        }
    }

}
