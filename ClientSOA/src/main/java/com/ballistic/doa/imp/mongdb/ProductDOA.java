package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.IProductDOA;
import com.ballistic.pojo.Product;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Set;

public class ProductDOA implements IProductDOA {

    private MongoDBContext mongoDBContext;

    public ProductDOA() { }

    public ProductDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Product product) throws Exception {
        try { // right now try catch no need but will soon need
            long startTime = System.nanoTime();
            if(product != null) {
                this.mongoDBContext.getDatastore().save(product);
            } else {
               throw new Exception("Product Must Contain Value");
            }
            this.catLogs("Save", startTime);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Product> product) throws Exception {
        try { // right now try catch no need but will soon need
            long startTime = System.nanoTime();
            if(product != null) {
                this.mongoDBContext.getDatastore().save(product);
            } else {
                throw new Exception("Product Must Contain Value");
            }
            this.catLogs("Save", startTime);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Product product) throws Exception {
        try { // right now try catch no need but will soon need
            long startTime = System.currentTimeMillis();
            this.catLogs("Update", startTime);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Product> products) throws Exception {
        try { // right now try catch no need but will soon need
            long startTime = System.currentTimeMillis();
            this.catLogs("Update", startTime);
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Product findById(String id) {
        long startTime = System.nanoTime();
        Product product = this.mongoDBContext.getDatastore().get(Product.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return product;
    }

    @Override
    public List<Product> fetchAllProduct() {
        long startTime = System.nanoTime();
        List<Product> products = this.mongoDBContext.getDatastore().find(Product.class).asList();
        this.catLogs("FetchAllProduct", startTime);
        return products;
    }

    @Override
    public List<Product> fetchAllProductBySupplier(Set<String> suppliers) {
        long startTime = System.nanoTime();
        List<Product> products = this.mongoDBContext.getDatastore().createQuery(Product.class)
            .field(SUPPLIER_ID).in(suppliers).asList();
        this.catLogs("FetchAllProductBySupplier", startTime);
        return products;
    }

    @Override
    public List<Product> fetchAllProductByCategories(Set<String> categories) {
        long startTime = System.nanoTime();
        List<Product> products = this.mongoDBContext.getDatastore().createQuery(Product.class)
            .field(CATEGORIES_ID).in(categories).asList();
        this.catLogs("FetchAllProductByCategories", startTime);
        return products;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}
