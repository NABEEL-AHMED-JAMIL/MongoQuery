package com.ballistic.doa.imp.jdbc;

import com.ballistic.doa.IProductDOA;
import com.ballistic.pojo.Product;

import java.util.List;
import java.util.Set;

public class ProductDOA implements IProductDOA {

    @Override
    public void save(Product value) {
        return;
    }

    @Override
    public void save(List<Product> value) {
        return;
    }

    @Override
    public void update(Product value) {
        return;
    }

    @Override
    public void update(List<Product> value) {
        return;
    }

    @Override
    public Product findById(String id) { return null; }

    @Override
    public List<Product> fetchAllProduct() { return null; }

    @Override
    public List<Product> fetchAllProductByCategories(Set<String> categories) { return null; }

    @Override
    public List<Product> fetchAllProductBySupplier(Set<String> suppliers) { return null; }

}
