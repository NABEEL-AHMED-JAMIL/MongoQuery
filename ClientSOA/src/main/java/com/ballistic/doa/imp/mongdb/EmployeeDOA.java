package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.IEmployeeDOA;
import com.ballistic.doa.query.FieldQuery;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Employee;
import com.mongodb.MongoSocketReadTimeoutException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeDOA implements IEmployeeDOA {

    private MongoDBContext mongoDBContext;

    public EmployeeDOA() { }

    public EmployeeDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Employee employee) {
        long startTime = System.nanoTime();
        this.getMongoDBContext().getDatastore().save(employee);
        this.catLogs("Save", startTime);
    }

    @Override
    public void save(List<Employee> employees) {
        long startTime = System.nanoTime();
        this.getMongoDBContext().getDatastore().save(employees);
        this.catLogs("Save", startTime);
    }

    @Override
    public void update(Employee employee) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(employee.getEmployeeId() != null) {
                Datastore dataStore = this.getMongoDBContext().getDatastore();
                Query<Employee> query = dataStore.createQuery(Employee.class)
                    .field(Mapper.ID_KEY).equal(new ObjectId(employee.getEmployeeId())).disableValidation();
                UpdateOperations<Employee> updateOps = dataStore.createUpdateOperations(Employee.class);

                if(employee.getLastName() != null) { updateOps.set(LAST_NAME, employee.getLastName()); }
                if (employee.getFirstName() != null) { updateOps.set(FIRST_NAME, employee.getFirstName()); }
                if(employee.getBirthDate() != null) { updateOps.set(BIRTH_DATE, employee.getBirthDate()); }
                if(employee.getPhoto() != null) { updateOps.set(PHOTO, employee.getPhoto()); }
                if(employee.getNotes() != null) { updateOps.set(NOTES, employee.getNotes()); }

                dataStore.update(query, updateOps, true);
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("Invalid Id " + employee.getEmployeeId());
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Employee> employees) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(employees != null) {
                employees.parallelStream().forEach(employee -> {
                    if(employee.getEmployeeId() != null) {
                        Datastore dataStore = this.getMongoDBContext().getDatastore();
                        Query<Employee> query = dataStore.createQuery(Employee.class).field(Mapper.ID_KEY)
                            .equal(employee.getEmployeeId()).disableValidation();
                        UpdateOperations<Employee> updateOps = dataStore.createUpdateOperations(Employee.class);

                        if(employee.getLastName() != null) { updateOps.set(LAST_NAME, employee.getLastName()); }
                        if (employee.getFirstName() != null) { updateOps.set(FIRST_NAME, employee.getFirstName()); }
                        if(employee.getBirthDate() != null) { updateOps.set(BIRTH_DATE, employee.getBirthDate()); }
                        if(employee.getPhoto() != null) { updateOps.set(PHOTO, employee.getPhoto()); }
                        if(employee.getNotes() != null) { updateOps.set(NOTES, employee.getNotes()); }

                        dataStore.update(query, updateOps, true);
                        this.catLogs("Update", startTime);
                    } else {
                        System.err.println("Invalid Id " + employee.getEmployeeId());
                    }
                });
            } else {
                throw new Exception("Invalid Size " + employees.size());
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<String> fetchAllId() {
        long startTime = System.nanoTime();
        List<String> ids = this.mongoDBContext.getDatastore().createQuery(Employee.class)
            .project(Mapper.ID_KEY, true).asList()
                .parallelStream().map(employee -> {
                    return employee.getEmployeeId();
                }).collect(Collectors.toList());
        this.catLogs("FetchAllId", startTime);
        return ids;
    }

    @Override
    public List<Employee> fetchAllNull() throws Exception {
        try {
            long startTime = System.nanoTime();
            Query<Employee> fetchAllNullQuery = this.mongoDBContext.getDatastore().find(Employee.class);
            this.getAllNullQuery().createQuery(fetchAllNullQuery);
            List<Employee> employees = fetchAllNullQuery.asList();
            this.catLogs("FetchAllNull" + " Total count :- " + employees.size(), startTime);
            return employees;
        } catch (MongoSocketReadTimeoutException ex) {
            throw ex;
        } catch (Exception ex) {
            System.err.println(ex.getLocalizedMessage());
            throw ex;
        }
    }

    @Override
    public Employee findById(String id) {
        long startTime = System.nanoTime();
        Employee employee = this.mongoDBContext.getDatastore().get(Employee.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees() {
        long startTime = System.nanoTime();
        List<Employee> employees = this.mongoDBContext.getDatastore().createQuery(Employee.class).asList();
        this.catLogs("FetchAllCustomer", startTime);
        return employees;
    }

    @Override
    public List<Employee> fetchAllByQuery(LocalQuery localQuery) {
        try {
            long startTime = System.nanoTime();
            Query<Employee> fetchQuery = this.mongoDBContext.getDatastore().find(Employee.class);
            localQuery.createQuery(fetchQuery);
            List<Employee> customers = fetchQuery.asList();
            this.catLogs("FetchAllByQuery" + " Total count :- " + customers.size(), startTime);
            return customers;
        } catch (MongoSocketReadTimeoutException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private LocalQuery getAllNullQuery() {
        LocalQuery localQuery = new LocalQuery();
        Set<FieldQuery> fieldQuery = new HashSet<>();
        fieldQuery.add(new FieldQuery(BIRTH_DATE, 0, null));
        fieldQuery.add(new FieldQuery(FIRST_NAME, 0, null));
        fieldQuery.add(new FieldQuery(LAST_NAME, 0, null));
        localQuery.setFilter(fieldQuery);
        return localQuery;
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }

}