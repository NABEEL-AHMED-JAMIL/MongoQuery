package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.ICustomerDOA;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Customer;
import com.mongodb.MongoSocketReadTimeoutException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerDOA implements ICustomerDOA {

    private MongoDBContext mongoDBContext;

    public CustomerDOA() { }

    public CustomerDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Customer customer) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(customer != null) {
                if(customer.getContactName() != null && !customer.getContactName().equals("")) {
                    if(this.findByContactName(customer.getContactName()) != null) {
                        throw new Exception("Customer ContactName Already Exist");
                    } else {
                        this.mongoDBContext.getDatastore().save(customer);
                        this.catLogs("Save", startTime);
                    }
                } else {
                    throw new Exception("Customer contact must have value ");
                }
            } else {
                throw new Exception("Customer have null value");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Customer> customers) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(customers != null) {
                List<Customer> existCustomer = this.findByContactName(this.getContactName(customers));
                if(existCustomer != null && existCustomer.size() > 0) {
                    throw new Exception("Customers Already Exist " + this.getContactName(existCustomer));
                } else {
                    this.mongoDBContext.getDatastore().save(customers);
                    this.catLogs("Save", startTime);
                }
            } else {
                throw new Exception("Customers have null");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Customer> findByContactName(Set<String> contactName) {
        long startTime = System.nanoTime();
        List<Customer> customers = this.mongoDBContext.getDatastore().createQuery(Customer.class)
           .field(CONTACT_NAME).in(contactName).asList();
        this.catLogs("FindByContactName", startTime);
        return customers;
    }

    @Override
    public void update(Customer customer) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(customer != null) {
                if(customer.getCustomerId() != null && customer.getContactName() != null) {
                    Datastore dataStore = getMongoDBContext().getDatastore();
                    Query<Customer> query = dataStore.createQuery(Customer.class).field(Mapper.ID_KEY).equal(new ObjectId(customer.getCustomerId()));
                    UpdateOperations<Customer> updateOps = dataStore.createUpdateOperations(Customer.class);
                    if(this.findByContactName(customer.getContactName()) == null) {
                        updateOps.set(CUSTOMER_NAME, customer.getContactName());
                    }
                    updateOps.set(CONTACT_NAME, customer.getContactName()).set(ADDRESS, customer.getAddress())
                    .set(CITY, customer.getCity()).set(POSTAL_CODE, customer.getPostalCode()).set(COUNTRY, customer.getCountry());
                    dataStore.update(query, updateOps, true);
                    this.catLogs("Update", startTime);
                } else {
                    if(customer.getCustomerId() == null && customer.getContactName() == null) {
                        throw new Exception("Update fail due to " + customer.getCustomerId()+","+customer.getContactName());
                    } else if(customer.getCustomerId() == null) {
                        throw new Exception("Update fail due to " + customer.getCustomerId());
                    } else if(customer.getContactName() == null) {
                        throw new Exception("Update fail due to contactName exist" + customer.getContactName());
                    }
                }
            } else {
                throw new Exception("Customer have null value");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Customer> customers) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(customers != null) {
                if(!this.isContactNamesNull(customers)) {
                    customers.stream().parallel().forEach(customer -> {
                        try {
                            this.update(customer);
                            this.catLogs("Update", startTime);
                        } catch (Exception ex) {
                            System.out.println("Error :- " + ex.getLocalizedMessage());
                        }
                    });
                } else {
                    throw new Exception("List Contain the null value for Customer Name");
                }
            } else {
                throw new Exception("Update fail due to null List");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Customer findById(String id) {
        long startTime = System.nanoTime();
        Customer customer = this.mongoDBContext.getDatastore().get(Customer.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return customer;
    }

    @Override
    public List<Customer> fetchAllCustomer() {
        long startTime = System.nanoTime();
        List<Customer> customers = this.mongoDBContext.getDatastore().createQuery(Customer.class).asList();
        this.catLogs("FetchAllCustomer", startTime);
        return customers;
    }

    @Override
    public Customer findByContactName(String contactName) {
        long startTime = System.nanoTime();
        Customer customer = this.mongoDBContext.getDatastore().createQuery(Customer.class)
            .field(CONTACT_NAME).equal(contactName).get();
        this.catLogs("FindByCategoryName", startTime);
        return customer;
    }

    @Override
    public List<Customer> fetchAllByQuery(LocalQuery localQuery) throws Exception {
        try {
            long startTime = System.nanoTime();
            Query<Customer> fetchQuery = this.mongoDBContext.getDatastore().find(Customer.class);
            localQuery.createQuery(fetchQuery);
            List<Customer> customers = fetchQuery.asList();
            this.catLogs("FetchAllByQuery" + " Total count :- " + customers.size(), startTime);
            return customers;
        } catch (MongoSocketReadTimeoutException ex) {
            throw ex;
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            throw ex;
        }
    }

    private Set<String> getContactName(List<Customer> customers) {
        return customers.stream().map(categorie -> { return categorie.getContactName();}).collect(Collectors.toSet());
    }

    private Boolean isContactNamesNull(List<Customer> customers) {
        Boolean isTrue = false;
        for (Customer customer :  customers) {
            if(customer.getContactName() == null || customer.getContactName().equals("")) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}
