package com.ballistic.manger;

import com.ballistic.doa.*;

public abstract class Manager {

    private ICategoriesDOA categoriesDOA;
    private ICustomerDOA customerDOA;
    private IEmployeeDOA employeeDOA;
    private IOrderDOA orderDOA;
    private IOrderDetailDOA orderDetailDOA;
    private IProductDOA productDOA;
    private IShipperDOA shipperDOA;
    private ISupplierDOA supplierDOA;

    public Manager() {}

    public ICategoriesDOA getCategoriesDOA() { return categoriesDOA; }
    protected void setCategoriesDOA(ICategoriesDOA categoriesDOA) { this.categoriesDOA = categoriesDOA; }

    public ICustomerDOA getCustomerDOA() { return customerDOA; }
    protected void setCustomerDOA(ICustomerDOA customerDOA) { this.customerDOA = customerDOA; }

    public IEmployeeDOA getEmployeeDOA() { return employeeDOA; }
    protected void setEmployeeDOA(IEmployeeDOA employeeDOA) { this.employeeDOA = employeeDOA; }

    public IOrderDOA getOrderDOA() { return orderDOA; }
    protected void setOrderDOA(IOrderDOA orderDOA) { this.orderDOA = orderDOA; }

    public IOrderDetailDOA getOrderDetailDOA() { return orderDetailDOA; }
    protected void setOrderDetailDOA(IOrderDetailDOA orderDetailDOA) { this.orderDetailDOA = orderDetailDOA; }

    public IProductDOA getProductDOA() { return productDOA; }
    protected void setProductDOA(IProductDOA productDOA) { this.productDOA = productDOA; }

    public IShipperDOA getShipperDOA() { return shipperDOA; }
    protected void setShipperDOA(IShipperDOA shipperDOA) { this.shipperDOA = shipperDOA; }

    public ISupplierDOA getSupplierDOA() { return supplierDOA; }
    protected void setSupplierDOA(ISupplierDOA supplierDOA) { this.supplierDOA = supplierDOA; }

}
