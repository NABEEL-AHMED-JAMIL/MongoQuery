package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.IOrderDetailDOA;
import com.ballistic.pojo.OrderDetail;
import java.util.List;

public class OrderDetailDOA implements IOrderDetailDOA {

    private LuceneDBContext luceneDBContext;

    public OrderDetailDOA() {}

    public OrderDetailDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

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
