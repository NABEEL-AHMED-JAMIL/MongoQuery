package com.ballistic.doa;

import com.ballistic.pojo.Product;

import java.util.List;
import java.util.Set;

public interface IProductDOA extends CrudDOA<Product> {

    public static final String PRODUCT_ID = "productId";
    public static final String PRODUCT_NAME = "productName";
    public static final String UNIT = "unit";
    public static final String PRICE = "price";
    public static final String SUPPLIER_ID = "supplierId";
    public static final String CATEGORIES_ID = "categoriesId";

    public List<Product> fetchAllProduct();
    public List<Product> fetchAllProductByCategories(Set<String> categories);
    public List<Product> fetchAllProductBySupplier(Set<String> suppliers);

}