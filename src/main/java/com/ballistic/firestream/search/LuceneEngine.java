package com.ballistic.firestream.search;

import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import com.ballistic.firestream.util.StockPriceUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LuceneEngine extends FSDirectoryLucene implements ILuceneEngine {

    private static Cache<String, List<StockPrice>> fetchCacheStockPrice =
            CacheBuilder.newBuilder().maximumSize(5000).expireAfterWrite(1, TimeUnit.MINUTES).build();

    public LuceneEngine() throws IOException { super(); }

    public LuceneEngine(String INDEX_DIR) throws IOException { super(INDEX_DIR); }

    @Override
    public void save(Document document) {
        long startTime = System.currentTimeMillis();
        try {
            IndexWriter writer = this.createWriter();
            writer.addDocument(document);
            writer.commit();
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error :- " +  ex.getLocalizedMessage());
        }
        System.out.println("Document Store Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    @Override
    public void save(List<Document> documents) {
        long startTime = System.currentTimeMillis();
        try {
            IndexWriter writer = this.createWriter();
            writer.addDocuments(documents);
            writer.commit();
            writer.close();
        } catch (IOException ex) {
            System.out.println("Error :- " +  ex.getLocalizedMessage());
        }
        System.out.println("Document Store Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }

    @Override
    public void update(Document document) {

    }

    @Override
    public void update(List<Document> documents) {
        // update the lis of document
    }

    @Override
    public StockPrice findById(FieldQuery fieldQuery) {
        long startTime = System.currentTimeMillis();
        StockPrice stockPrice = null;
        Document stockPriceDocument = null;
        try {
            IndexSearcher searcher =  this.createSearcher();
            if(StockPriceUtil.isIdQuery(fieldQuery)) {
                QueryParser queryParser = new QueryParser(fieldQuery.getField(), this.getAnalyzer());
                Query query = queryParser.parse(fieldQuery.getValue().toString());
                TopDocs hits = searcher.search(query, 1);
                System.out.println("Query :- " + query + " Success hits :- " + hits.totalHits);
                if(hits.totalHits > 0) {
                    for (ScoreDoc scoreDoc : hits.scoreDocs) {
                        stockPriceDocument = searcher.doc(scoreDoc.doc);
                        stockPrice = StockPriceUtil.documentToStockPrice(stockPriceDocument);
                    }
                }
                this.getReader().close();
            }
        } catch (IOException ex) {
            System.err.println("Error :- " + ex.getLocalizedMessage());
        } catch (ParseException ex) {
            System.err.println("Error :- " + ex.getLocalizedMessage());
        }
        System.out.println("Lucene Fetch Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return stockPrice;
    }

    @Override
    public List<StockPrice> getAllByQuery(List<FieldQuery> fieldQueries) {
        long startTime = System.currentTimeMillis();
        System.out.println("Db Fetch Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return null;
    }

}
