package com.ballistic.doa;

import com.ballistic.pojo.Categories;

import java.util.List;

public interface ICategoriesDOA extends CrudDOA<Categories> {

    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String DESCRIPTION = "description";

    public List<String> getAllCategoriesNames();
    public List<Categories> getAllCategories();
    public Categories findByCategoryName(String categoryName);
    public List<Categories> findCategoryNames(List<String> categoryNames);

}
