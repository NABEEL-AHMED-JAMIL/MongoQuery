package com.ballistic.doa.imp.jdbc;

import com.ballistic.doa.ICategoriesDOA;
import com.ballistic.pojo.Categories;

import java.util.List;

public class CategoriesDOA implements ICategoriesDOA {

    @Override
    public void save(Categories categories) {
        return;
    }

    @Override
    public void save(List<Categories> categories) {
        return;
    }

    @Override
    public void update(Categories categories) {
        return;
    }

    @Override
    public void update(List<Categories> categories) {
        return;
    }

    @Override
    public List<String> getAllCategoriesNames() { return null; }

    @Override
    public List<Categories> getAllCategories() { return null; }

    @Override
    public Categories findById(String id) { return null; }

    @Override
    public Categories findByCategoryName(String categoryName) { return null; }

    @Override
    public List<Categories> findCategoryNames(List<String> categoryNames) { return null; }
}
