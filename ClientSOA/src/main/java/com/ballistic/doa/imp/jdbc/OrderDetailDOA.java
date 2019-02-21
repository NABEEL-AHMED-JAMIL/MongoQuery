package com.ballistic.doa.imp.jdbc;

import com.ballistic.doa.IOrderDetailDOA;
import com.ballistic.pojo.OrderDetail;

import java.util.List;

public class OrderDetailDOA implements IOrderDetailDOA {

    @Override
    public void save(OrderDetail orderDetail) {
        return;
    }

    @Override
    public void save(List<OrderDetail> orderDetails) {
        return;
    }

    @Override
    public void update(OrderDetail orderDetail) {
        return;
    }

    @Override
    public void update(List<OrderDetail> orderDetails) {
        return;
    }

    @Override
    public OrderDetail findById(String id) { return null; }

    @Override
    public List<OrderDetail> fetchAllOrderDetail() { return null; }
}
