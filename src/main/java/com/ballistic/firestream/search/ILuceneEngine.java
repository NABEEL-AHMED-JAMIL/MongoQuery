package com.ballistic.firestream.search;

import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import org.apache.lucene.document.Document;

import java.util.List;

public interface ILuceneEngine {

    // save-process
    public void save(Document document);
    public void save(List<Document> documents);

    public void update(Document document);
    public void update(List<Document> documents);

    // Fetch
    public StockPrice findById(FieldQuery fieldQuery);
    public List<StockPrice> getAllByQuery(List<FieldQuery> fieldQueries);

}
