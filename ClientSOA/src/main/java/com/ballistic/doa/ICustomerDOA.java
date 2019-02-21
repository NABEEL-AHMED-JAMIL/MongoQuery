package com.ballistic.doa;

import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Customer;

import java.util.List;
import java.util.Set;

public interface ICustomerDOA extends CrudDOA<Customer> {

    public static final String CUSTOMER_ID = "customerId";
    public static final String CUSTOMER_NAME = "customerName";
    public static final String CONTACT_NAME = "contactName"; //unieq
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String POSTAL_CODE = "postalCode";
    public static final String COUNTRY = "country";

    public List<Customer> findByContactName(Set<String> contactName);
    public Customer findByContactName(String contactName);
    public List<Customer> fetchAllCustomer();
    public List<Customer> fetchAllByQuery(LocalQuery query) throws Exception;

}
