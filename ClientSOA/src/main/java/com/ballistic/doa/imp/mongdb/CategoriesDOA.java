package com.ballistic.doa.imp.mongdb;

import com.ballistic.coredel.MongoDBContext;
import com.ballistic.doa.ICategoriesDOA;
import com.ballistic.pojo.Categories;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriesDOA implements ICategoriesDOA {

    private MongoDBContext mongoDBContext;

    public CategoriesDOA() { }

    public CategoriesDOA(MongoDBContext mongoDBContext) {
        this.mongoDBContext = mongoDBContext;
    }

    public MongoDBContext getMongoDBContext() { return mongoDBContext; }
    public void setMongoDBContext(MongoDBContext mongoDBContext) { this.mongoDBContext = mongoDBContext; }

    @Override
    public void save(Categories categories) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(categories != null) {
                this.validateCategoris(categories);
                this.getMongoDBContext().getDatastore().save(categories);
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("Categories Null");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void save(List<Categories> categories) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(categories != null) {
                this.validateCategoris(categories);
                this.getMongoDBContext().getDatastore().save(categories);
                this.catLogs("Save", startTime);
            } else {
                throw new Exception("Categories Null");
            }
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(Categories categories) throws Exception {
        try {
            long startTime = System.nanoTime();
            if(categories != null) {
                this.validateCategoris(categories);
                Datastore dataStore = getMongoDBContext().getDatastore();
                Query<Categories> query = dataStore.createQuery(Categories.class)
                     .field(Mapper.ID_KEY).equal(new ObjectId(categories.getCategoryId()));
                UpdateOperations<Categories> update = dataStore.createUpdateOperations(Categories.class)
                     .set(CATEGORY_NAME, categories.getCategoryName());
                this.getMongoDBContext().getDatastore().update(query, update);
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("Categories Null");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void update(List<Categories> categories) throws Exception {
        try {
            if(categories != null) {
                long startTime = System.nanoTime();
                this.validateCategoris(categories);
                Datastore dataStore = getMongoDBContext().getDatastore();
                categories.parallelStream().forEach(categories1 -> {
                    Query<Categories> query = dataStore.createQuery(Categories.class)
                         .field(Mapper.ID_KEY).equal(new ObjectId(categories1.getCategoryId()));
                    UpdateOperations<Categories> update = dataStore.createUpdateOperations(Categories.class)
                         .set(CATEGORY_NAME, categories1.getCategoryName());
                    dataStore.update(query, update);
                });
                this.catLogs("Update", startTime);
            } else {
                throw new Exception("Categories Null");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<String> getAllCategoriesNames() {
        long startTime = System.nanoTime();
        List<String> categoryNames = this.mongoDBContext.getDatastore()
            .createQuery(Categories.class).project(CATEGORY_NAME,true)
            .asList().parallelStream().map(categories -> {
                return categories.getCategoryName();
            }).collect(Collectors.toList());
        this.catLogs("FindCategoryNames", startTime);
        return categoryNames;
    }

    @Override
    public List<Categories> getAllCategories() {
        long startTime = System.nanoTime();
        List<Categories> categories = this.mongoDBContext.getDatastore()
            .createQuery(Categories.class).project(CATEGORY_NAME,true).asList();
        this.catLogs("FindCategoryNames", startTime);
        return categories;
    }

    @Override
    public Categories findById(String id) {
        long startTime = System.nanoTime();
        Categories categories = this.mongoDBContext.getDatastore().get(Categories.class, new ObjectId(id));
        this.catLogs("FindById", startTime);
        return categories;
    }

    @Override
    public Categories findByCategoryName(String categoryName) {
        long startTime = System.nanoTime();
        Categories categories = this.mongoDBContext.getDatastore()
            .createQuery(Categories.class).field(CATEGORY_NAME).equal(categoryName).get();
        this.catLogs("FindByCategoryName", startTime);
        return categories;
    }

    @Override
    public List<Categories> findCategoryNames(List<String> categoryNames) {
        long startTime = System.nanoTime();
        List<Categories> categories = this.mongoDBContext.getDatastore()
            .createQuery(Categories.class).field(CATEGORY_NAME).in(categoryNames).asList();
        this.catLogs("FindCategoryNames", startTime);
        return categories;
    }

    private List<String> getCategoryNames(List<Categories> categories) {
        return categories.stream().map(categorie -> { return categorie.getCategoryName(); }).collect(Collectors.toList());
    }

    private void validateCategoris(Categories categories) throws Exception {
        if(this.findByCategoryName(categories.getCategoryName()) != null) {
            throw new Exception("Error :- Name Already Exist " + categories.getCategoryName());
        } else if (categories.getCategoryName() == null || categories.getCategoryName().equals("")) {
            throw new Exception("Error :- Invalid Store Request " + categories.getCategoryName());
        }
    }

    private void validateCategoris(List<Categories> categories) throws Exception {
        if(categories.size() < 0 ) {
            throw new Exception("Error :- Categories List Empty");
        }
        List<Categories> categoryAllReadyExist = this.findCategoryNames(this.getCategoryNames(categories));
        if(categoryAllReadyExist != null) {
            throw new Exception("Error :- Name Already Exist " + this.getCategoryNames(categoryAllReadyExist).toString());
        }
    }

    private void catLogs(String functionName, Long startTime) {
        System.out.println("Db " + functionName + " Process Time :- " + (System.nanoTime() - startTime)/1000000 + " ms.");
    }
}