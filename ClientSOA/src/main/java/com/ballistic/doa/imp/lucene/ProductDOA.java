package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.IProductDOA;
import com.ballistic.pojo.Product;
import java.util.List;
import java.util.Set;

public class ProductDOA implements IProductDOA {

    private LuceneDBContext luceneDBContext;

    public ProductDOA() {}

    public ProductDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

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
    public List<Product> fetchAllProductByCategories(Set<String> categories) {
        return null;
    }

    @Override
    public List<Product> fetchAllProductBySupplier(Set<String> suppliers) {
        return null;
    }

}
