package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.ISupplierDOA;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Supplier;
import com.mongodb.MongoSocketReadTimeoutException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.*;
import java.util.stream.Collectors;

public class SupplierDOA implements ISupplierDOA {

    private MongoDBContext mongoDBContext;

    public SupplierDOA() { }

    public SupplierDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Supplier supplier) throws Exception  {
        try {
            long startTime = System.nanoTime();
            this.supplierSave(supplier);
            this.catLogs("Save", startTime);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Supplier> suppliers) throws Exception  {
        try {
            long startTime = System.nanoTime();
            suppliers.parallelStream().forEach(supplier -> {
                try {
                    this.supplierSave(supplier);
                    this.catLogs("Save", startTime);
                } catch (Exception ex) {
                    System.err.println("Error :- " + ex.getLocalizedMessage());
                }
            });
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Supplier supplier) throws Exception  {
        try {
            long startTime = System.nanoTime();
            if(supplier.getSupplierId() != null) {
                Datastore dataStore = this.getMongoDBContext().getDatastore();
                Query<Supplier> query = dataStore.createQuery(Supplier.class).field(Mapper.ID_KEY)
                    .equal(new ObjectId(supplier.getSupplierId())).disableValidation();
                UpdateOperations<Supplier> updateOps = dataStore.createUpdateOperations(Supplier.class);

                if(supplier.getSupplierName() != null) { updateOps.set(SUPPLIER_NAME, supplier.getSupplierName()); }

                if(supplier.getContactName() != null && this.isContain(CONTACT_NAME, supplier.getContactName(), 1)) {
                    updateOps.set(CONTACT_NAME, supplier.getContactName());
                } else {
                    throw new Exception("Contact Name Already Exist");
                }

                if(supplier.getAddress() != null) { updateOps.set(ADDRESS, supplier.getAddress()); }

                if(supplier.getCity() != null) { updateOps.set(CITY, supplier.getCity()); }

                if(supplier.getPostalCode() != null && this.isContain(POSTAL_CODE, supplier.getPostalCode(), 2)) {
                    updateOps.set(POSTAL_CODE, supplier.getPostalCode());
                } else {
                    throw new Exception("Postal Code Already Exist");
                }

                if(supplier.getCountry() != null) { updateOps.set(COUNTRY, supplier.getCountry()); }

                if(supplier.getPhone() != null && this.isContain(PHONE, supplier.getPhone(), 3)) {
                    updateOps.set(PHONE, supplier.getPhone());
                } else {
                    throw new Exception("Postal Code Already Exist");
                }

                dataStore.update(query, updateOps, true);
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("Invalid Id " + supplier.getSupplierId());
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Supplier> suppliers) throws Exception  {
        try {
            long startTime = System.nanoTime();
             suppliers.parallelStream().forEach(supplier -> {
                if(supplier.getSupplierId() != null) {
                    Datastore dataStore = this.getMongoDBContext().getDatastore();
                    Query<Supplier> query = dataStore.createQuery(Supplier.class).field(Mapper.ID_KEY)
                        .equal(new ObjectId(supplier.getSupplierId())).disableValidation();
                    UpdateOperations<Supplier> updateOps = dataStore.createUpdateOperations(Supplier.class);

                    if(supplier.getSupplierName() != null) { updateOps.set(SUPPLIER_NAME, supplier.getSupplierName()); }

                    if(supplier.getContactName() != null && this.isContain(CONTACT_NAME, supplier.getContactName(), 1)) {
                        updateOps.set(CONTACT_NAME, supplier.getContactName());
                    } else {
                        System.err.println("Contact Name Already Exist");
                    }

                    if(supplier.getAddress() != null) { updateOps.set(ADDRESS, supplier.getAddress()); }

                    if(supplier.getCity() != null) { updateOps.set(CITY, supplier.getCity()); }

                    if(supplier.getPostalCode() != null && this.isContain(POSTAL_CODE, supplier.getPostalCode(), 2)) {
                        updateOps.set(POSTAL_CODE, supplier.getPostalCode());
                    } else {
                        System.err.println("Postal Code Already Exist");
                    }

                    if(supplier.getCountry() != null) { updateOps.set(COUNTRY, supplier.getCountry()); }

                    if(supplier.getPhone() != null && this.isContain(PHONE, supplier.getPhone(), 3)) {
                        updateOps.set(PHONE, supplier.getPhone());
                    } else {
                        System.err.println("Postal Code Already Exist");
                    }

                    dataStore.update(query, updateOps, true);
                    this.catLogs("Update", startTime);
                } else {
                    System.err.println("Invalid Id " + supplier.getSupplierId());
                }
            });
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Supplier findById(String id) {
        long startTime = System.nanoTime();
        Supplier supplier = this.mongoDBContext.getDatastore().get(Supplier.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return supplier;
    }

    @Override
    public Map<String, Set<String>> fetchOptionalQuery(Integer type) {
        long startTime = System.nanoTime();
        HashMap<String, Set<String>> fetchOption = new HashMap<>();
        if(type ==1 ) {
            fetchOption.put(CITY, this.fetchQuery(CITY));
        } else if(type == 2) {
            fetchOption.put(POSTAL_CODE, this.fetchQuery(POSTAL_CODE));
        } else if(type == 3) {
            fetchOption.put(COUNTRY, this.fetchQuery(COUNTRY));
        } else if(type == 4) {
            fetchOption.put(PHONE, this.fetchQuery(PHONE));
        } else if(type == 5) {
            fetchOption.put(CONTACT_NAME, this.fetchQuery(CONTACT_NAME));
        } else {
            fetchOption.put(CONTACT_NAME, this.fetchQuery(CONTACT_NAME));
            fetchOption.put(CITY, this.fetchQuery(CITY));
            fetchOption.put(POSTAL_CODE, this.fetchQuery(POSTAL_CODE));
            fetchOption.put(COUNTRY, this.fetchQuery(COUNTRY));
            fetchOption.put(PHONE, this.fetchQuery(PHONE));
        }
        this.catLogs("FetchOptionalQuery", startTime);
        return fetchOption;
    }

    @Override
    public List<Supplier> fetchAllByQuery(LocalQuery localQuery) {
        try {
            long startTime = System.nanoTime();
            Query<Supplier> fetchQuery = this.mongoDBContext.getDatastore().find(Supplier.class);
            localQuery.createQuery(fetchQuery);
            List<Supplier> suppliers = fetchQuery.asList();
            this.catLogs("FetchAllByQuery" + " Total count :- " + suppliers.size(), startTime);
            return suppliers;
        } catch (MongoSocketReadTimeoutException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<Supplier> fetchAll() {
        long startTime = System.nanoTime();
        List<Supplier> suppliers = this.mongoDBContext.getDatastore().createQuery(Supplier.class).asList();
        this.catLogs("FetchAllCustomer", startTime);
        return suppliers;
    }

    private Set<String> fetchQuery(String field) {
        return this.mongoDBContext.getDatastore().find(Supplier.class)
            .project(field, true).project(Mapper.ID_KEY, false).asList().parallelStream()
                .map(supplier ->  {
                    String value = null;
                    if(supplier.getCity() != null) {
                        value = supplier.getCity();
                    } else if(supplier.getPostalCode() != null) {
                        value = supplier.getPostalCode();
                    } else if(supplier.getCountry() != null) {
                        value = supplier.getCountry();
                    } else if(supplier.getPhone() != null) {
                        value = supplier.getPhone();
                    } else if(supplier.getContactName() != null) {
                        value = supplier.getContactName();
                    }
                    return value;
            }).collect(Collectors.toSet());
    }

    private void supplierSave(Supplier supplier) throws Exception {
        if(supplier != null) {
            if ((supplier.getSupplierName() != null) &&
                (supplier.getContactName() != null && this.isContain(CONTACT_NAME, supplier.getContactName(), 1)) &&
                (supplier.getAddress() != null && (supplier.getCity() != null && supplier.getCountry() != null)) &&
                (supplier.getPostalCode() != null && this.isContain(POSTAL_CODE, supplier.getPostalCode(), 2)) &&
                (supplier.getPhone() != null && this.isContain(PHONE, supplier.getPhone(), 3))) {
                this.mongoDBContext.getDatastore().save(supplier);
            } else {
                throw new Exception("Already Exist");
            }
        } else {
            throw new Exception("Supplier Must Have Value");
        }
    }

    private Boolean isContactNameContain(String contactName) {
        return this.mongoDBContext.getDatastore().createQuery(Supplier.class).field(CONTACT_NAME).equal(contactName).get() == null ? true : false;
    }

    private Boolean isPostalCodeContain(String postalCode) {
        return this.mongoDBContext.getDatastore().createQuery(Supplier.class).field(POSTAL_CODE).equal(postalCode).get() == null ? true : false;
    }

    private Boolean isPhoneContain(String phone) {
        return this.mongoDBContext.getDatastore().createQuery(Supplier.class).field(PHONE).equal(phone).get() == null ? true : false;
    }

    private Boolean isContain(String field, String value, Integer type) {
        if(field.equals(CONTACT_NAME) && type == 1) {
            return this.isContactNameContain(value);
        } else if(field.equals(POSTAL_CODE) && type == 2) {
            return this.isPostalCodeContain(value);
        } else if (field.equals(PHONE) && type == 3) {
            return this.isPhoneContain(value);
        } else {
            return false;
        }
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }

}