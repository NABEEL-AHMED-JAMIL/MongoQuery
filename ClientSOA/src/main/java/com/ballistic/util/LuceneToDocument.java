package com.ballistic.util;

import com.ballistic.pojo.*;
import org.apache.lucene.document.*;

import static com.ballistic.doa.ICategoriesDOA.CATEGORY_ID;
import static com.ballistic.doa.ICategoriesDOA.CATEGORY_NAME;
import static com.ballistic.doa.ICategoriesDOA.DESCRIPTION;
import static com.ballistic.doa.ICustomerDOA.*;
import static com.ballistic.doa.IEmployeeDOA.*;
import static com.ballistic.doa.IOrderDOA.*;
import static com.ballistic.doa.IOrderDOA.CUSTOMER_ID;
import static com.ballistic.doa.IOrderDOA.EMPLOYEE_ID;
import static com.ballistic.doa.IOrderDetailDOA.ORDER_DETAIL_ID;
import static com.ballistic.doa.IOrderDetailDOA.QUANTITY;
import static com.ballistic.doa.IProductDOA.*;
import static com.ballistic.doa.IShipperDOA.PHONE;
import static com.ballistic.doa.IShipperDOA.SHIPPER_NAME;
import static com.ballistic.doa.ISupplierDOA.SUPPLIER_NAME;


public class LuceneToDocument {

    private LuceneToDocument() { throw new AssertionError("No instances for you!"); }

    public Document categoriesDocument(Categories categories) {
        Document document = new Document();
        document.add(new StringField(CATEGORY_ID, categories.getCategoryId(), Field.Store.YES));
        document.add(new StringField(CATEGORY_NAME,categories.getCategoryName(),Field.Store.YES));
        document.add(new StringField(DESCRIPTION, categories.getDescription(), Field.Store.NO));
        return document;
    }

    public Document customerDocument(Customer customer) {
        Document document = new Document();
        document.add(new StringField(CUSTOMER_ID, customer.getCustomerId(), Field.Store.YES));
        document.add(new StringField(CUSTOMER_NAME,customer.getCustomerName(),Field.Store.NO));
        document.add(new StringField(CONTACT_NAME, customer.getContactName(), Field.Store.YES));
        document.add(new StringField(ADDRESS,customer.getAddress(),Field.Store.NO));
        document.add(new StringField(CITY, customer.getCity(), Field.Store.YES));
        document.add(new StringField(POSTAL_CODE,customer.getPostalCode(),Field.Store.YES));
        document.add(new StringField(COUNTRY, customer.getCountry(), Field.Store.YES));
        return document;
    }

    public Document employeeDocument(Employee employee) {
        Document document = new Document();
        document.add(new StringField(EMPLOYEE_ID, employee.getEmployeeId(), Field.Store.YES));
        document.add(new StringField(LAST_NAME,employee.getLastName(),Field.Store.NO));
        document.add(new StringField(FIRST_NAME, employee.getFirstName(), Field.Store.NO));
        document.add(new StringField(BIRTH_DATE, DateTools.dateToString(employee.getBirthDate(), DateTools.Resolution.DAY),Field.Store.YES));
        document.add(new StringField(PHOTO, employee.getPhoto(), Field.Store.YES));
        employee.getNotes().parallelStream().forEach(note -> { document.add(new StringField(NOTES, note, Field.Store.YES)); });
        return document;
    }

    public Document orderDetailDocument(OrderDetail orderDetail) {
        Document document = new Document();
        document.add(new StringField(ORDER_DETAIL_ID, orderDetail.getOrderDetailId(), Field.Store.YES));
        document.add(new StringField(PRODUCT_ID, orderDetail.getProductId(), Field.Store.YES));
        document.add(new StringField(QUANTITY, String.valueOf(orderDetail.getQuantity()), Field.Store.YES));
        // document.add(new StringField(ORDER_ID, orderDetail.getOrderId(), Field.Store.YES)); // will added soon
        return document;
    }

    public Document orderDocument(Order order) {
        Document document = new Document();
        document.add(new StringField(ORDER_ID, order.getOrderId(), Field.Store.YES));
        document.add(new StringField(CUSTOMER_ID, order.getCustomerId(), Field.Store.YES));
        document.add(new StringField(EMPLOYEE_ID, order.getEmployeeId(), Field.Store.YES));
        document.add(new StringField(ORDER_DATE, DateTools.dateToString(order.getOrderDate(), DateTools.Resolution.DAY),Field.Store.YES));
        document.add(new StringField(SHIPPER_ID, order.getShipperId(), Field.Store.YES));
        order.getOrderDetailIds().parallelStream().forEach(ord -> { document.add(new StringField(ORDER_DETAIL_IDS, ord, Field.Store.YES)); });
        return document;
    }

    public Document productDocument(Product product) {
        Document document = new Document();
        document.add(new StringField(PRODUCT_ID, product.getProductId(), Field.Store.YES));
        document.add(new StringField(PRODUCT_NAME, product.getProductName(), Field.Store.YES));
        document.add(new StringField(UNIT, product.getUnit(), Field.Store.YES));
        document.add(new DoublePoint(PRICE, product.getPrice()));
        document.add(new StringField(SUPPLIER_ID, product.getSupplierId(), Field.Store.YES));
        document.add(new StringField(CATEGORIES_ID, product.getCategoriesId(), Field.Store.YES));
        return document;
    }

    public Document productDocument(Shipper shipper) {
        Document document = new Document();
        document.add(new StringField(SHIPPER_ID, shipper.getShipperId(), Field.Store.YES));
        document.add(new StringField(SHIPPER_NAME, shipper.getShipperName(), Field.Store.YES));
        document.add(new StringField(PHONE, shipper.getPhone(), Field.Store.YES));
        return document;
    }

    public Document productDocument(Supplier supplier) {
        Document document = new Document();
        document.add(new StringField(SUPPLIER_ID, supplier.getSupplierId(), Field.Store.YES));
        document.add(new StringField(SUPPLIER_NAME, supplier.getSupplierName(), Field.Store.NO));
        document.add(new StringField(CONTACT_NAME, supplier.getContactName(), Field.Store.YES));
        document.add(new StringField(ADDRESS, supplier.getAddress(), Field.Store.NO));
        document.add(new StringField(CITY, supplier.getCity(), Field.Store.YES));
        document.add(new StringField(POSTAL_CODE, supplier.getPostalCode(), Field.Store.YES));
        document.add(new StringField(COUNTRY, supplier.getCountry(), Field.Store.YES));
        document.add(new StringField(PHONE, supplier.getPhone(), Field.Store.YES));
        return document;
    }
}