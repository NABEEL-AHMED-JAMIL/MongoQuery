package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.IOrderDetailDOA;
import com.ballistic.pojo.OrderDetail;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class OrderDetailDOA implements IOrderDetailDOA {

    private MongoDBContext mongoDBContext;

    public OrderDetailDOA() { }

    public OrderDetailDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(OrderDetail orderDetail) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(orderDetail != null && isValidOrderDetail(orderDetail)) {
                this.mongoDBContext.getDatastore().save(orderDetail);
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("InValid Order-Detail");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<OrderDetail> orderDetails) throws Exception {
        try {
            long startTime = System.nanoTime();
            orderDetails.forEach(orderDetail -> {
                if(orderDetail != null && isValidOrderDetail(orderDetail)) {
                    this.mongoDBContext.getDatastore().save(orderDetail);
                    this.catLogs("Save", startTime);
                }
            });
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(OrderDetail orderDetail) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(orderDetail != null && isValidOrderDetail(orderDetail)) {
                Datastore dataStore = getMongoDBContext().getDatastore();
                Query<OrderDetail> query = dataStore.createQuery(OrderDetail.class)
                    .field(Mapper.ID_KEY).equal(new ObjectId(orderDetail.getOrderDetailId()));
                UpdateOperations<OrderDetail> update = dataStore.createUpdateOperations(OrderDetail.class)
                    .set(PRODUCT_ID, orderDetail.getProductId()).set(QUANTITY, orderDetail.getQuantity());
                this.getMongoDBContext().getDatastore().update(query, update);
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("OrderDetail Null");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<OrderDetail> orderDetails)throws Exception {
        try {
            long startTime = System.nanoTime();
            Datastore dataStore = getMongoDBContext().getDatastore();
            for (OrderDetail orderDetail: orderDetails) {
                if(orderDetail != null && isValidOrderDetail(orderDetail)) {
                    Query<OrderDetail> query = dataStore.createQuery(OrderDetail.class)
                        .field(Mapper.ID_KEY).equal(new ObjectId(orderDetail.getOrderDetailId()));
                    UpdateOperations<OrderDetail> update = dataStore.createUpdateOperations(OrderDetail.class)
                        .set(PRODUCT_ID, orderDetail.getProductId()).set(QUANTITY, orderDetail.getQuantity());
                    this.getMongoDBContext().getDatastore().update(query, update);
                    this.catLogs("Update", startTime);
                } else {
                    System.err.println("OrderDetail Null");
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public OrderDetail findById(String id) {
        long startTime = System.nanoTime();
        OrderDetail orderDetail = this.mongoDBContext.getDatastore().get(OrderDetail.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return orderDetail;
    }

    @Override
    public List<OrderDetail> fetchAllOrderDetail() {
        long startTime = System.nanoTime();
        List<OrderDetail> orderDetails = this.mongoDBContext.getDatastore().createQuery(OrderDetail.class).asList();
        this.catLogs("FetchAllOrderDetail", startTime);
        return orderDetails;
    }

    private Boolean isValidOrderDetail(OrderDetail orderDetail) {
        Boolean isValid = false;
        if (orderDetail.getProductId() != null && orderDetail.getQuantity() != null) {
            isValid = true;
            return isValid;
        }
        return isValid;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}
