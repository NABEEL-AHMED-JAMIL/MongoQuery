package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.IOrderDOA;
import com.ballistic.pojo.Order;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class OrderDOA implements IOrderDOA {

    private MongoDBContext mongoDBContext;

    public OrderDOA() { }

    public OrderDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Order order) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(order != null && this.validateOrder(order)) {
                 this.getMongoDBContext().getDatastore().save(order);
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("Order Not Valid");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Order> orders) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(orders != null) {
                orders.parallelStream().forEach(order -> {
                    if(this.validateOrder(order)) {
                        this.getMongoDBContext().getDatastore().save(order);
                        this.catLogs("Save", startTime);
                    } else {
                        System.out.println("Order Not Valid");
                    }
                });
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("Order Not Valid");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Order order) throws Exception {
        try {
            long startTime = System.nanoTime();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Order> orders) throws Exception {
        try {
            long startTime = System.nanoTime();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Order findById(String id) {
        long startTime = System.nanoTime();
        Order order = this.mongoDBContext.getDatastore().get(Order.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return order;
    }

    @Override
    public List<Order> findOrderByDates(Date startDate, Date endDate) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(ORDER_DATE).greaterThanOrEq(startDate).field(ORDER_DATE).lessThanOrEq(endDate).asList();
        this.catLogs("FindOrderByDates", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrder() {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class).asList();
        this.catLogs("FindCategoryNames", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderByEmployee(String employeeId) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(EMPLOYEE_ID).equal(employeeId).asList();
        this.catLogs("FindAllOrderByEmployee", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderByEmployeeWithDate(String employeeId,Date currentDate) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(EMPLOYEE_ID).equal(employeeId).field(ORDER_DATE).equal(currentDate).asList();
        this.catLogs("FindAllOrderByEmployeeWithDate", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderByEmployeeRangeDate(String employeeId,Date startDate, Date endDate) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(EMPLOYEE_ID).equal(employeeId).field(ORDER_DATE).greaterThanOrEq(startDate)
            .field(ORDER_DATE).lessThanOrEq(endDate).asList();
        this.catLogs("FindAllOrderByEmployeeRangeDate", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderBySupplier(String supplierId) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(SHIPPER_ID).equal(supplierId).asList();
        this.catLogs("FindAllOrderBySupplier", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderBySupplierWithDate(String supplierId,Date currentDate) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(SHIPPER_ID).equal(supplierId).field(ORDER_DATE).equal(currentDate).asList();
        this.catLogs("FindAllOrderBySupplierWithDate", startTime);
        return orders;
    }

    @Override
    public List<Order> findAllOrderBySupplierWithRangeData(String supplierId,Date startDate, Date endDate) {
        long startTime = System.nanoTime();
        List<Order> orders = this.mongoDBContext.getDatastore().createQuery(Order.class)
            .field(SHIPPER_ID).equal(supplierId).field(ORDER_DATE).greaterThanOrEq(startDate)
            .field(ORDER_DATE).lessThanOrEq(endDate).asList();
        this.catLogs("FindAllOrderBySupplierWithRangeData", startTime);
        return orders;
    }

    private Boolean validateOrder(Order order) {
        Boolean isValid = false;
        if(order.getCustomerId()!= null && order.getEmployeeId() != null
            && order.getOrderDate() != null && order.getShipperId() != null) {
            isValid = true;
        }
        return isValid;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}
