package com.ballistic.doa.imp.lucene;

import com.ballistic.coredel.LuceneDBContext;
import com.ballistic.doa.ICategoriesDOA;
import com.ballistic.pojo.Categories;

import java.io.IOException;
import java.util.List;

public class CategoriesDOA implements ICategoriesDOA {

    private LuceneDBContext luceneDBContext;

    public CategoriesDOA() {}

    public CategoriesDOA(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    public LuceneDBContext getLuceneDBContext() { return luceneDBContext; }
    public void setLuceneDBContext(LuceneDBContext luceneDBContext) { this.luceneDBContext = luceneDBContext; }

    @Override
    public void save(Categories categories) throws IOException {
        this.luceneDBContext.index(this.luceneDBContext.getLuceneToDocument().categoriesDocument(categories));
    }

    @Override
    public void save(List<Categories> categories) {
        categories.parallelStream().forEach(categories1 -> {
            try {
                this.luceneDBContext.index(this.luceneDBContext.getLuceneToDocument().categoriesDocument(categories1));
            } catch (IOException ex) {
                System.out.println("Error :- " + ex);
            }
        });
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
