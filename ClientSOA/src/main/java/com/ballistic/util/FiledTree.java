package com.ballistic.util;

import com.google.gson.Gson;
import java.util.HashSet;
import java.util.Set;

public class FiledTree {

    private String key;
    private String keyType;
    private Set<FiledTree> filedNode;

    public FiledTree() {}

    public FiledTree(String key, String keyType) {
        this.key = key;
        this.keyType = keyType;
    }

    public FiledTree(String key, String keyType, Set<FiledTree> filedNode) {
        this.key = key;
        this.keyType = keyType;
        this.filedNode = filedNode;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getKeyType() { return keyType; }
    public void setKeyType(String keyType) { this.keyType = keyType; }

    public Set<FiledTree> getFiledNode() { return filedNode; }
    public void setFiledNode(Set<FiledTree> filedNode) { this.filedNode = filedNode; }

    public static Set<FiledTree> getCategoriesField() {
        Set<FiledTree> catField = new HashSet<>();
        catField.add(new FiledTree("categoryId", "str"));
        catField.add(new FiledTree("categoryName", "str"));
        catField.add(new FiledTree("description", "str"));
        return catField;
    }

    public static Set<FiledTree> getCustomerField() {
        Set<FiledTree> cutField = new HashSet<>();
        cutField.add(new FiledTree("customerId", "str"));
        cutField.add(new FiledTree("categoryName", "str"));
        cutField.add(new FiledTree("description", "str"));
        cutField.add(new FiledTree("contactName", "str"));
        cutField.add(new FiledTree("address", "str"));
        cutField.add(new FiledTree("postalCode", "str"));
        cutField.add(new FiledTree("city", "str"));
        cutField.add(new FiledTree("country", "str"));
        cutField.add(new FiledTree("description", "str"));
        return cutField;
    }

    public static Set<FiledTree> getEmployeeFiled() {
        Set<FiledTree> enpField = new HashSet<>();
        enpField.add(new FiledTree("employeeId", "str"));
        enpField.add(new FiledTree("lastName", "str"));
        enpField.add(new FiledTree("firstName", "str"));
        enpField.add(new FiledTree("birthDate", "dat"));
        enpField.add(new FiledTree("photo", "str"));
        enpField.add(new FiledTree("notes", "text"));
        return enpField;
    }

    public static Set<FiledTree> getShipperFiled() {
        Set<FiledTree> shipField = new HashSet<>();
        shipField.add(new FiledTree("shipperId", "str"));
        shipField.add(new FiledTree("shipperName", "str"));
        shipField.add(new FiledTree("phone", "str"));
        return shipField;
    }

    public static Set<FiledTree> getSupplierFiled() {
        Set<FiledTree> supField = new HashSet<>();
        supField.add(new FiledTree("supplierId", "str"));
        supField.add(new FiledTree("supplierName", "str"));
        supField.add(new FiledTree("contactName", "str"));
        supField.add(new FiledTree("address", "str"));
        supField.add(new FiledTree("city", "str"));
        supField.add(new FiledTree("postalCode", "str"));
        supField.add(new FiledTree("country", "str"));
        supField.add(new FiledTree("phone", "str"));
        return supField;
    }

    public static Set<FiledTree> getProductrFiled() {
        Set<FiledTree> pudField = new HashSet<>();
        pudField.add(new FiledTree("productId", "str"));
        pudField.add(new FiledTree("productName", "str"));
        pudField.add(new FiledTree("unit", "str"));
        pudField.add(new FiledTree("price", "fat"));
        pudField.add(new FiledTree("supplier", "obj", getSupplierFiled()));
        pudField.add(new FiledTree("categories", "obj", getCategoriesField()));
        return pudField;
    }

    public static Set<FiledTree> getOrderFiled() {
        Set<FiledTree> ordField = new HashSet<>();
        ordField.add(new FiledTree("orderId", "str"));
        ordField.add(new FiledTree("customer", "obj", getCustomerField()));
        ordField.add(new FiledTree("employee", "obj", getEmployeeFiled()));
        ordField.add(new FiledTree("orderDate", "dat"));
        ordField.add(new FiledTree("shipper", "obj", getShipperFiled()));
        ordField.add(new FiledTree("orderDetail", "obj", getOrderDetailFiled()));
        return ordField;
    }

    public static Set<FiledTree> getOrderDetailFiled() {
        Set<FiledTree> ordDtailField = new HashSet<>();
        ordDtailField.add(new FiledTree("orderDetailId", "str"));
        ordDtailField.add(new FiledTree("product", "obj", getProductrFiled()));
        ordDtailField.add(new FiledTree("quantity", "int"));
        return ordDtailField;
    }

    @Override
    public String toString() { return new Gson().toJson(this); }

//    public static void main(String args[]) {
//        HashMap<String, Set<FiledTree>> fieldTree = new HashMap<>();
//        fieldTree.put("Categories", getCategoriesField());
//        fieldTree.put("Customer", getCustomerField());
//        fieldTree.put("Employee", getEmployeeFiled());
//        fieldTree.put("Order", getOrderFiled());
//        fieldTree.put("OrderDetail", getOrderDetailFiled());
//        fieldTree.put("Product", getProductrFiled());
//        fieldTree.put("Shipper", getShipperFiled());
//        fieldTree.put("Supplier", getSupplierFiled());
//        System.out.println(fieldTree);
//    }
}