package com.ballistic.doa.imp.jdbc;

import com.ballistic.doa.IOrderDOA;
import com.ballistic.pojo.Order;

import java.util.Date;
import java.util.List;

public class OrderDOA implements IOrderDOA {

    @Override
    public void save(Order value) {
        return;
    }

    @Override
    public void save(List<Order> value) {
        return;
    }

    @Override
    public void update(Order value) {
        return;
    }

    @Override
    public void update(List<Order> value) {
        return;
    }

    @Override
    public Order findById(String id) { return null; }

    @Override
    public List<Order> findOrderByDates(Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Order> findAllOrder() {
        return null;
    }

    @Override
    public List<Order> findAllOrderByEmployee(String employeeId) {
        return null;
    }

    @Override
    public List<Order> findAllOrderByEmployeeWithDate(String employeeId, Date currentDate) {
        return null;
    }

    @Override
    public List<Order> findAllOrderByEmployeeRangeDate(String employeeId, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public List<Order> findAllOrderBySupplier(String supplierId) {
        return null;
    }

    @Override
    public List<Order> findAllOrderBySupplierWithDate(String supplier, Date currentDate) {
        return null;
    }

    @Override
    public List<Order> findAllOrderBySupplierWithRangeData(String supplier, Date startDate, Date endDate) {
        return null;
    }
}
