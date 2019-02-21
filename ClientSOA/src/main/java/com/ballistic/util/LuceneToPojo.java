package com.ballistic.util;

import com.ballistic.pojo.*;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;

import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.ballistic.doa.ICategoriesDOA.CATEGORY_ID;
import static com.ballistic.doa.ICategoriesDOA.CATEGORY_NAME;
import static com.ballistic.doa.ICategoriesDOA.DESCRIPTION;
import static com.ballistic.doa.ICustomerDOA.*;
import static com.ballistic.doa.IEmployeeDOA.*;
import static com.ballistic.doa.IOrderDOA.*;
import static com.ballistic.doa.IOrderDOA.CUSTOMER_ID;
import static com.ballistic.doa.IOrderDOA.EMPLOYEE_ID;
import static com.ballistic.doa.IOrderDetailDOA.ORDER_DETAIL_ID;
import static com.ballistic.doa.IOrderDetailDOA.PRODUCT_ID;
import static com.ballistic.doa.IOrderDetailDOA.QUANTITY;
import static com.ballistic.doa.IProductDOA.*;
import static com.ballistic.doa.IShipperDOA.PHONE;
import static com.ballistic.doa.IShipperDOA.SHIPPER_NAME;
import static com.ballistic.doa.ISupplierDOA.SUPPLIER_NAME;

public class LuceneToPojo {

    private LuceneToPojo() { throw new AssertionError("No instances for you!"); }

    public Categories categoriesDocument(Document document) {
        Categories categories = new Categories();
        categories.setCategoryId(document.get(CATEGORY_ID));
        categories.setCategoryName(document.get(CATEGORY_NAME));
        categories.setDescription(document.get(DESCRIPTION));
        return categories;
    }

    public Customer customerDocument(Document document) {
        Customer customer = new Customer();
        customer.setCustomerId(document.get(CUSTOMER_ID));
        customer.setCustomerName(document.get(CUSTOMER_NAME));
        customer.setContactName(document.get(CONTACT_NAME));
        customer.setAddress(document.get(ADDRESS));
        customer.setCity(document.get(CITY));
        customer.setCountry(document.get(COUNTRY));
        customer.setPostalCode(document.get(POSTAL_CODE));
        return customer;
    }

    public Employee employeeDocument(Document document) {
        Employee employee = new Employee();
        employee.setEmployeeId(document.get(EMPLOYEE_ID));
        employee.setLastName(document.get(LAST_NAME));
        employee.setFirstName(document.get(FIRST_NAME));
        try {
            employee.setBirthDate(DateTools.stringToDate(document.get(BIRTH_DATE)));
        } catch (ParseException ex) {
            System.err.println("Error :- " + ex.getLocalizedMessage());
        }
        employee.setPhoto(document.get(PHOTO));
        employee.setNotes(Arrays.stream(document.getFields(NOTES))
           .map(IndexableField::stringValue).collect(Collectors.toList()));
        return employee;
    }

    public OrderDetail orderDetailDocument(Document document) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(document.get(ORDER_DETAIL_ID));
        orderDetail.setProductId(document.get(PRODUCT_ID));
        orderDetail.setQuantity(Integer.valueOf(document.get(QUANTITY)));
        // orderDetail.setOrderId(document.get(ORDER_ID));
        return orderDetail;
    }

    public Order orderDocument(Document document) {
        Order order = new Order();
        order.setOrderId(document.get(ORDER_ID));
        order.setCustomerId(document.get(CUSTOMER_ID));
        order.setEmployeeId(document.get(EMPLOYEE_ID));
        try {
            order.setOrderDate(DateTools.stringToDate(document.get(ORDER_DATE)));
        } catch (ParseException ex) {
            System.err.println("Error :- " + ex.getLocalizedMessage());
        }
        order.setShipperId(document.get(SHIPPER_ID));
        order.setOrderDetailIds(Arrays.stream(document.getFields(ORDER_DETAIL_IDS))
           .map(IndexableField::stringValue).collect(Collectors.toList()));
        return order;
    }

    public Product productDocument(Document document) {
        Product product = new Product();
        product.setProductId(document.get(PRODUCT_ID));
        product.setProductName(document.get(PRODUCT_NAME));
        product.setUnit(document.get(UNIT));
        product.setPrice(Double.valueOf(document.get(PRICE)));
        product.setSupplierId(document.get(SUPPLIER_ID));
        product.setCategoriesId(document.get(CATEGORIES_ID));
        return product;
    }

    public Shipper shipperDocument(Document document) {
        Shipper shipper = new Shipper();
        shipper.setShipperId(document.get(SHIPPER_ID));
        shipper.setShipperName(document.get(SHIPPER_NAME));
        shipper.setPhone(document.get(PHONE));
        return shipper;
    }

    public Supplier supplierDocument(Document document) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(document.get(SUPPLIER_ID));
        supplier.setSupplierName(document.get(SUPPLIER_NAME));
        supplier.setAddress(document.get(ADDRESS));
        supplier.setCity(document.get(CITY));
        supplier.setContactName(document.get(CONTACT_NAME));
        supplier.setCountry(document.get(COUNTRY));
        supplier.setPhone(document.get(PHONE));
        supplier.setPostalCode(document.get(POSTAL_CODE));
        return supplier;
    }
}