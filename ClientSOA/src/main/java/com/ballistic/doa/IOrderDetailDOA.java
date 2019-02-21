package com.ballistic.doa;

import com.ballistic.pojo.OrderDetail;

import java.util.List;

public interface IOrderDetailDOA extends CrudDOA<OrderDetail> {

    public static final String ORDER_DETAIL_ID = "orderDetailId";
    public static final String PRODUCT_ID = "productId";
    public static final String QUANTITY = "quantity";
    public static final String ORDER_ID = "orderId";

    public List<OrderDetail> fetchAllOrderDetail();

}
