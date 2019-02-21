package com.ballistic.doa.imp.jdbc;

import com.ballistic.doa.ISupplierDOA;
import com.ballistic.doa.query.LocalQuery;
import com.ballistic.pojo.Supplier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SupplierDOA implements ISupplierDOA {

    @Override
    public void save(Supplier value) { }

    @Override
    public void save(List<Supplier> value) { }

    @Override
    public void update(Supplier value) {
        return;
    }

    @Override
    public void update(List<Supplier> value) {
        return;
    }

    @Override
    public Supplier findById(String id) { return null; }

    @Override
    public Map<String, Set<String>> fetchOptionalQuery(Integer type) { return null; }

    @Override
    public List<Supplier> fetchAllByQuery(LocalQuery localQuery) { return null; }

    @Override
    public List<Supplier> fetchAll() { return null; }

}
