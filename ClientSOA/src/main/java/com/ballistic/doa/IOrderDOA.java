package com.ballistic.doa;

import com.ballistic.pojo.Order;

import java.util.Date;
import java.util.List;

public interface IOrderDOA extends CrudDOA<Order>  {

    public static final String ORDER_ID = "orderId";
    public static final String CUSTOMER_ID = "customerId";
    public static final String EMPLOYEE_ID = "employeeId";
    public static final String ORDER_DATE = "orderDate";
    public static final String SHIPPER_ID = "shipperId";
    public static final String ORDER_DETAIL_IDS = "orderDetailIds";

    public List<Order> findOrderByDates(Date startDate, Date endDate);
    public List<Order> findAllOrder();
    public List<Order> findAllOrderByEmployee(String employeeId);
    public List<Order> findAllOrderByEmployeeWithDate(String employeeId,Date currentDate);
    public List<Order> findAllOrderByEmployeeRangeDate(String employeeId,Date startDate, Date endDate);
    public List<Order> findAllOrderBySupplier(String supplierId);
    public List<Order> findAllOrderBySupplierWithDate(String supplier,Date currentDate);
    public List<Order> findAllOrderBySupplierWithRangeData(String supplier, Date startDate, Date endDate);

}
