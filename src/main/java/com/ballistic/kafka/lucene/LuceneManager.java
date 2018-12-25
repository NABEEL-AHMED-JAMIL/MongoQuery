package com.ballistic.kafka.lucene;

import com.ballistic.kafka.lucene.service.IIndexDocument;
import com.ballistic.kafka.lucene.service.DeviceIndexDocument;
import com.ballistic.kafka.lucene.util.LuceneStatus;

import java.util.List;

public class LuceneManager<D> {

    private D data;
    private List<D> datas;
    private String key;
    private String value;
    private IIndexDocument indexDocument;

    public LuceneManager() {}

    public LuceneManager(DeviceIndexDocument indexDocument) {
        this.indexDocument = indexDocument;
    }

    public LuceneManager(DeviceIndexDocument indexDocument, D data) {
        this.indexDocument = indexDocument;
        this.data = data;
    }

    public LuceneManager(String key, DeviceIndexDocument indexDocument) {
        this.key = key;
        this.indexDocument = indexDocument;
    }

    public LuceneManager(DeviceIndexDocument indexDocument, String value) {
        this.indexDocument = indexDocument;
        //this.type = type;
        this.value = value;
    }

    public LuceneManager(IIndexDocument indexDocument, List<D> datas) {
        this.indexDocument = indexDocument;
        this.datas = datas;
    }

    public IIndexDocument getIndexDocument() { return indexDocument; }
    public void setIndexDocument(IIndexDocument indexDocument) { this.indexDocument = indexDocument; }

    public D getData() { return data; }
    public void setData(D data) { this.data = data; }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    //public void setType(T type) { this.type = type; }

    public List<D> getDatas() { return datas; }
    public void setDatas(List<D> datas) { this.datas = datas; }


    public void saveIndex(D document) {
        this.getIndexDocument().saveIndex(document);
    }

    public void saveIndex() {
        try {
            if(this.getData() != null) {
                this.getIndexDocument().saveIndex(this.data);
            }else {
                throw new NullPointerException("Data Contain Null Value");
            }
        }catch (NullPointerException e) {
            System.out.println("Error " +  e.getLocalizedMessage());
        }
    }

    public void saveIndexes(List<D> documents) {
        this.getIndexDocument().saveIndexes(documents);
    }

    public void saveIndexes() {
        try {
            if(this.getDatas() != null) {
                this.getIndexDocument().saveIndexes(this.datas);
            }else {
                throw new NullPointerException("Data Contain Null Value");
            }
        }catch (NullPointerException e) {
            System.out.println("Error " +  e.getLocalizedMessage());
        }
    }


//    public T process(LuceneStatus status) {
//        switch (status) {
//            case WRITE:
//
//                break;
//            case WRITE_ALL:
//
//                break;
//            case DELETE:
//                this.indexDocument.delete();
//                break;
//            case DELETE_ONE:
//                this.indexDocument.deleteOnc(this.getKey());
//                break;
//            case SEARCH_ONE:
//                this.indexDocument.findOnce(this.getKey());
//                break;
//            case SEARCH:
//                this.indexDocument.findAll(this.getValue());
//                break;
//            case UPDATE:
//                this.indexDocument.update(this.getDatas());
//                break;
//        }
//        return null;
//    }
}
