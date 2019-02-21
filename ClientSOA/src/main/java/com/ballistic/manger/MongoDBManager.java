package com.ballistic.manger;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.imp.mongdb.*;

public class MongoDBManager<T> extends Manager {

    private MongoDBContext dbContext;

    public MongoDBManager() {}

    public MongoDBManager(MongoDBContext dbContext) {
        this.dbContext = dbContext;
    }

    public MongoDBManager(MongoDBContext dbContext, T object) {
        this.dbContext = dbContext;
        if(object instanceof CategoriesDOA) {
            this.setCategoriesDOA((CategoriesDOA) object);
        } else if(object instanceof CustomerDOA) {
            this.setCustomerDOA((CustomerDOA) object);
        } else if(object instanceof CategoriesDOA) {
            this.setCategoriesDOA((CategoriesDOA) object);
        } else if(object instanceof EmployeeDOA) {
            this.setEmployeeDOA((EmployeeDOA) object);
        } else if(object instanceof OrderDOA) {
            this.setOrderDOA((OrderDOA) object);
        } else if(object instanceof OrderDetailDOA) {
            this.setOrderDetailDOA((OrderDetailDOA) object);
        } else if(object instanceof ProductDOA) {
            this.setProductDOA((ProductDOA) object);
        } else if(object instanceof ShipperDOA) {
            this.setShipperDOA((ShipperDOA) object);
        } else if(object instanceof SupplierDOA) {
            this.setSupplierDOA((SupplierDOA) object);
        }
    }

    public MongoDBContext getDbContext() { return dbContext; }
    public void setDbContext(MongoDBContext dbContext) { this.dbContext = dbContext; }

}
