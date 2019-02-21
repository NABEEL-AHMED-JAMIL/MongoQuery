package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.IEmployeeDOA;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Employee;
import java.util.List;

public class EmployeeDOA implements IEmployeeDOA {

    private LuceneDBContext luceneDBContext;

    public EmployeeDOA() {}

    public EmployeeDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    @Override
    public void save(Employee employee) {
        return;
    }

    @Override
    public void save(List<Employee> employees) {
        return;
    }

    @Override
    public void update(Employee employee) {
        return;
    }

    @Override
    public void update(List<Employee> employees) {
        return;
    }

    @Override
    public Employee findById(String id) { return null; }

    @Override
    public List<Employee> findAllEmployees() {
        return null;
    }

    @Override
    public List<Employee> fetchAllByQuery(LocalQuery query) {
        return null;
    }

    @Override
    public List<String> fetchAllId() { return null; }

    @Override
    public List<Employee> fetchAllNull() { return null; }

}
