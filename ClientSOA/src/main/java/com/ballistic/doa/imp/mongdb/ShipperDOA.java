package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.IShipperDOA;
import com.ballistic.pojo.Shipper;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

public class ShipperDOA implements IShipperDOA {

    private MongoDBContext mongoDBContext;

    public ShipperDOA() { }

    public ShipperDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Shipper shipper) throws Exception {
        try {
            long startTime = System.nanoTime();
            if((shipper.getPhone() != null && isPhoneContain(shipper.getPhone())) && shipper.getShipperName() != null) {
                this.getMongoDBContext().getDatastore().save(shipper);
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("Phone Already Exist");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Shipper> shippers) throws Exception {
        try {
            long startTime = System.nanoTime();
            shippers.parallelStream().forEach(shipper -> {
                if((shipper.getPhone() != null && isPhoneContain(shipper.getPhone())) && shipper.getShipperName() != null) {
                    this.getMongoDBContext().getDatastore().save(shipper);
                    this.catLogs("Save", startTime);
                } else {
                    System.err.println("Phone Already Exist");
                }
            });
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Shipper shipper) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(shipper != null && shipper.getShipperId() != null) {
                Datastore dataStore = getMongoDBContext().getDatastore();
                Query<Shipper> query = dataStore.createQuery(Shipper.class).field(Mapper.ID_KEY).equal(new ObjectId(shipper.getShipperId()));
                UpdateOperations<Shipper> updateOps = dataStore.createUpdateOperations(Shipper.class);
                if(shipper.getShipperName() != null) { updateOps.set(SHIPPER_NAME, shipper.getShipperName()); }
                if(shipper.getPhone() != null && this.isPhoneContain(shipper.getPhone())) { updateOps.set(PHONE, shipper.getPhone()); }
                dataStore.update(query, updateOps, true);
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("Customer have null value");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Shipper> shippers) throws Exception {
        try {
            long startTime = System.nanoTime();
            shippers.parallelStream().forEach(shipper -> {
                if(shipper != null && shipper.getShipperId() != null) {
                    Datastore dataStore = getMongoDBContext().getDatastore();
                    Query<Shipper> query = dataStore.createQuery(Shipper.class).field(Mapper.ID_KEY).equal(new ObjectId(shipper.getShipperId()));
                    UpdateOperations<Shipper> updateOps = dataStore.createUpdateOperations(Shipper.class);
                    if(shipper.getShipperName() != null) { updateOps.set(SHIPPER_NAME, shipper.getShipperName()); }
                    if(shipper.getPhone() != null && this.isPhoneContain(shipper.getPhone())) { updateOps.set(PHONE, shipper.getPhone()); }
                    dataStore.update(query, updateOps, true);
                    this.catLogs("Update", startTime);
                } else {
                    System.err.println("Customer have null value");
                }
            });
            this.catLogs("Update", startTime);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Shipper findById(String id) {
        long startTime = System.nanoTime();
        Shipper shippers = this.mongoDBContext.getDatastore().get(Shipper.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return shippers;
    }

    @Override
    public List<Shipper> fetchAll() {
        long startTime = System.nanoTime();
        List<Shipper> shippers = this.mongoDBContext.getDatastore().createQuery(Shipper.class).asList();
        this.catLogs("FetchAllCustomer", startTime);
        return shippers;
    }

    private Boolean isPhoneContain(String phone) {
        return this.mongoDBContext.getDatastore().createQuery(Shipper.class).field(PHONE).equal(phone).get() == null ? true : false;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}
