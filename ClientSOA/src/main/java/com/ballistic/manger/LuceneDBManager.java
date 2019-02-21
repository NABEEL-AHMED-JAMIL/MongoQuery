package com.ballistic.manger;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.imp.lucene.*;

public class LuceneDBManager <T> extends Manager {

    private LuceneDBContext luceneDBContext;

    public LuceneDBManager() { }

    public LuceneDBManager(LuceneDBContext luceneDBContext) {
        this.luceneDBContext = luceneDBContext;
    }

    public LuceneDBManager(LuceneDBContext luceneDBContext, T object) {
        this.luceneDBContext = luceneDBContext;
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

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

}
