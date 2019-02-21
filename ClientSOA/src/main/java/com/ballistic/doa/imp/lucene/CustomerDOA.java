package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.ICustomerDOA;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Customer;
import java.util.List;
import java.util.Set;

public class CustomerDOA implements ICustomerDOA {

    private LuceneDBContext luceneDBContext;

    public CustomerDOA() {}

    public CustomerDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    @Override
    public void save(Customer customer) {
        return;
    }

    @Override
    public void save(List<Customer> customers) {
        return;
    }

    @Override
    public void update(Customer customer) {
        return;
    }

    @Override
    public void update(List<Customer> customers) {
        return;
    }

    @Override
    public Customer findById(String id) { return null; }

    @Override
    public List<Customer> findByContactName(Set<String> contactName) {
        return null;
    }

    @Override
    public Customer findByContactName(String contactName) {
        return null;
    }

    @Override
    public List<Customer> fetchAllCustomer() {
        return null;
    }

    @Override
    public List<Customer> fetchAllByQuery(LocalQuery query) {
        return null;
    }

}
