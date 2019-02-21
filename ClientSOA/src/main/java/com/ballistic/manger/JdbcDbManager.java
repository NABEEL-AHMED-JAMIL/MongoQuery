package com.ballistic.manger;

import com.ballistic.coredel.JdbcDBContext;
import com.ballistic.doa.imp.jdbc.*;

public class JdbcDbManager<T> extends Manager {

    private JdbcDBContext jdbcDBContext;

    public JdbcDbManager() {}

    public JdbcDbManager(JdbcDBContext jdbcDBContext) {
        this.jdbcDBContext = jdbcDBContext;
    }

    public JdbcDbManager(JdbcDBContext jdbcDBContext, T object) {
        this.jdbcDBContext = jdbcDBContext;
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

    public JdbcDBContext getJdbcDBContext() { return jdbcDBContext; }
    public void setJdbcDBContext(JdbcDBContext jdbcDBContext) { this.jdbcDBContext = jdbcDBContext; }

}
