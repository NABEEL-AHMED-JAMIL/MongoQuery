package com.ballistic.firestream.search;

import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import org.apache.lucene.document.Document;

import java.util.List;

public class LuceneEngineManager {

    private ILuceneEngine iLuceneEngine;

    public LuceneEngineManager(ILuceneEngine iLuceneEngine) {
        System.out.println("StockPriceManager:- Constructor...Init");
        this.iLuceneEngine = iLuceneEngine;
        System.out.println("StockPriceManager:- Constructor...End");
    }

    public void save(Document document) { this.iLuceneEngine.save(document); }

    public void save(List<Document> documents) { this.iLuceneEngine.save(documents); }

    public void update(Document document) { this.iLuceneEngine.update(document); }

    public void update(List<Document> documents) { this.iLuceneEngine.update(documents); }

    public StockPrice findById(FieldQuery fieldQuery) { return this.iLuceneEngine.findById(fieldQuery); }

    public List<StockPrice> getAllByQuery(List<FieldQuery> fieldQueries) { return this.iLuceneEngine.getAllByQuery(fieldQueries); }

}
