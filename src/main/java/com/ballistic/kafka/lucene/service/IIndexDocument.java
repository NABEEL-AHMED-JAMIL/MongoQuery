package com.ballistic.kafka.lucene.service;

import java.util.List;

public interface IIndexDocument<D> {

    public void saveIndex(D document);
    public void saveIndexes(List<D> documents);
    public D findOnce(String key);
    public List<D> findAll(String value);
    public void update(List<D> document);
    // check the key
    public Boolean deleteOnc(String key);
    public Boolean delete();

}
