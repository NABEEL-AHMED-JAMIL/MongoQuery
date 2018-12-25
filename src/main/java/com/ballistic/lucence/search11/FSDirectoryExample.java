package com.ballistic.lucence.search11;

import com.ballistic.firestream.pojo.StockPrice;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static com.ballistic.firestream.util.IStockPrice.*;


public class FSDirectoryExample {

    private static final String INDEX_DIR = "G://lucene";
    private Analyzer analyzer;
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter writer;
    private IndexReader reader;

    public FSDirectoryExample() throws IOException {
        this.setAnalyzer(new StandardAnalyzer());
        this.setDirectory(FSDirectory.open(Paths.get(INDEX_DIR)));
    }

    private Analyzer getAnalyzer() { return analyzer; }
    private void setAnalyzer(Analyzer analyzer) { this.analyzer = analyzer; }

    private Directory getDirectory() { return directory; }
    private void setDirectory(Directory directory) { this.directory = directory; }

    private IndexWriterConfig getIndexWriterConfig() { return indexWriterConfig; }
    private void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) { this.indexWriterConfig = indexWriterConfig; }

    private IndexWriter getWriter() { return writer; }
    private void setWriter(IndexWriter writer) { this.writer = writer; }
    // will close
    public void writerClose() throws IOException { this.getWriter().close(); }

    private IndexReader getReader() { return reader; }
    private void setReader(IndexReader reader) { this.reader = reader; }
    // will close
    public void readerClose() throws IOException { this.getReader().close(); }

    private IndexWriter createWriter() throws IOException {
        this.setIndexWriterConfig(new IndexWriterConfig(this.getAnalyzer()));
        this.setWriter(new IndexWriter(this.getDirectory(), this.getIndexWriterConfig()));
        return getWriter();
    }

    private IndexSearcher createSearcher() throws IOException {
        this.setReader(DirectoryReader.open(getDirectory()));
        return new IndexSearcher(this.getReader());
    }

    public void writeIndex(List<Account> accounts) throws IOException {
        this.writer = createWriter();
        this.writer.deleteAll();
        // first convert account's to document
        List<Document> documents = accounts.stream().map(account -> {
            Document document = new Document();
            document.add(new TextField("id", account.getId(), Field.Store.YES));
            document.add(new TextField("username", account.getUsername(), Field.Store.YES));
            document.add(new TextField("password", account.getPassword(), Field.Store.YES));
            document.add(new StringField("status", account.getStatus().toString(), Field.Store.YES));
            document.add(new TextField("phone", account.getPhone(), Field.Store.YES));
            System.out.println(" " + account.toString());
            System.out.println("+------------------------------------" + "------------------------------" +
                    "---------------------" + "---------------------" + "-------------------------+");
            return document;
        }).collect(Collectors.toList());
        // delete all before adding the new index..
        this.writer.addDocuments(documents);
        this.writer.commit();
        this.writerClose();
    }

    /**
     * Method Call to update the Lucence data at once time durring fetching the data from the https stream and
     * adding both data into th db and Lucene
     * */
    public void writeIndexForStock(StockPrice stockPrice) throws IOException {
        long startTime = System.currentTimeMillis();
        // only 1 time need to create write
        this.writer = createWriter();
        Document document = new Document();
        if(stockPrice.getUuId() != null) {
            document.add(new StringField(UUID, stockPrice.getUuId(),Field.Store.YES));
            System.out.println("UUID Add in Document :- " + stockPrice.getUuId());
        }
        if(stockPrice.getDate() != null) {
            document.add(new StringField(DATE, String.valueOf(stockPrice.getDate()), Field.Store.YES));
            System.out.println("Date Add in Document :- " + stockPrice.getDate());
        }

        if(stockPrice.getOpenPrice() != null) {
            document.add(new DoublePoint(OPEN_PRICE, stockPrice.getOpenPrice()));
            System.out.println("Open Price Add in Document :- " + stockPrice.getOpenPrice());
        }

        if(stockPrice.getHighPrice() != null) {
            document.add(new DoublePoint(HIGH_PRICE, stockPrice.getHighPrice()));
            System.out.println("High Price Add in Document :- " + stockPrice.getHighPrice());
        }

        if(stockPrice.getLowPrice() != null) {
            document.add(new DoublePoint(LOW_PRICE, stockPrice.getLowPrice()));
            System.out.println("Low Price Add in Document :- " + stockPrice.getLowPrice());
        }

        if(stockPrice.getClosePrice() != null) {
            document.add(new DoublePoint(CLOSE_PRICE, stockPrice.getClosePrice()));
            System.out.println("Close Price Add in Document :- " + stockPrice.getClosePrice());
        }

        if(stockPrice.getWap() != null) {
            document.add(new DoublePoint(WAP, stockPrice.getWap()));
            System.out.println("Wap Add in Document :- " + stockPrice.getWap());
        }

        if(stockPrice.getNoOfShares() != null) {
            document.add(new DoublePoint(NO_OF_SHARES, stockPrice.getNoOfShares()));
            System.out.println("No of Shares Add in Document :- " + stockPrice.getNoOfShares());
        }

        if(stockPrice.getNoOfTrades() != null) {
            document.add(new IntPoint(NO_OF_TRADES, stockPrice.getNoOfTrades()));
            System.out.println("No of Trades Add in Document :- " + stockPrice.getNoOfTrades());
        }

        if(stockPrice.getNoOfTrades() != null) {
            document.add(new DoublePoint(TOTAL_TURNOVER, stockPrice.getTotalTurnover()));
            System.out.println("Total Turnover Add in Document :- " + stockPrice.getNoOfTrades());
        }

        if(stockPrice.getDeliverableQuantity() != null) {
            document.add(new IntPoint(DELIVERABLE_QUANTITY, stockPrice.getDeliverableQuantity()));
            System.out.println("Deliverable Quantity Add in Document :- " + stockPrice.getDeliverableQuantity());
        }

        if(stockPrice.getDeliQtyToTradedQty() != null) {
            document.add(new DoublePoint(DELIQTY_TO_TREADEDQTY, stockPrice.getDeliQtyToTradedQty()));
            System.out.println("Deliqty To Treadqty Add in Document :- " + stockPrice.getDeliQtyToTradedQty());
        }

        if(stockPrice.getSpreadHighLow() != null) {
            document.add(new DoublePoint(SPREAD_HIGH_LOW, stockPrice.getSpreadHighLow()));
            System.out.println("Spread High Low Add in Document :- " + stockPrice.getSpreadHighLow());
        }

        if(stockPrice.getSpreadCloseOpen() != null) {
            document.add(new DoublePoint(SPREAD_CLOSE_OPEN, stockPrice.getSpreadCloseOpen()));
            System.out.println("Spread Close Open Add in Document :- " + stockPrice.getSpreadCloseOpen());
        }

        System.out.println(" Document Save IN Lucene :- " + stockPrice.toString());
        System.out.println("+------------------------------------");
        this.writer.addDocument(document);
        this.writer.commit();
        this.writerClose();
        System.out.println("Lucene Store Process Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
    }



    public void searchIndex(String fieldSearch, String  value, Integer result) throws IOException, ParseException {
        IndexSearcher searcher =  this.createSearcher();
        QueryParser queryParser = new QueryParser(fieldSearch, this.getAnalyzer());
        Query q = new TermQuery(new Term(fieldSearch, value));
        Query query = queryParser.parse(value);
        //Search by firstName
        TopDocs foundDocs = searcher.search(query, result);
        if(foundDocs.totalHits > 0) {
            System.out.println("Total Results :: " + foundDocs.totalHits);
            for (ScoreDoc scoreDoc : foundDocs.scoreDocs) {
                Document d = searcher.doc(scoreDoc.doc);
                System.out.println("Document ID | " + d.get("id") + " | Username | " + d.get("username")
                        + " | Password | " + d.get("password") + " | Status | " + d.get("status")
                        + " |  Phone | " + d.get("phone") + " | Score : " + scoreDoc.score);
                System.out.println("+-----------+----------------------------------" + "----+----------+---------------------------------" +
                        "-----------------------------------------------" + "------------------+");
            }
        }
        this.getReader().close();
    }

    public void searchIndex(String query) throws IOException, ParseException {
        long startTime = System.currentTimeMillis();
        IndexSearcher searcher =  this.createSearcher();
        QueryParser queryParser = new QueryParser("status", new StandardAnalyzer());
        Query query1 = queryParser.parse(query);
        System.out.println("Type of query: " + query1.getClass().getSimpleName());
        System.out.println("Query :- " + query);
        TopDocs foundDocs = searcher.search(query1, 100);

        if(foundDocs.totalHits > 0) {
            System.out.println("Total Results :: " + foundDocs.totalHits);
            for (ScoreDoc scoreDoc : foundDocs.scoreDocs) {
                try {
                    Thread.sleep(5);
                    Document d = searcher.doc(scoreDoc.doc);
                    System.out.println("Document ID | " + d.get("id") + " | Username | " + d.get("username")
                            + " | Password | " + d.get("password") + " | Status | " + d.get("status")
                            + " |  Phone | " + d.get("phone") + " | Score : " + scoreDoc.score);
                    System.out.println("+-----------+----------------------------------" + "----+----------+--------" +
                            "-------------------------" + "--------------------------------------------" +
                            "---------------------+");
                } catch (InterruptedException e) {
                    System.out.println("Error " + e.getLocalizedMessage());
                }
            }
        }
        this.getReader().close();
        System.out.println("Total time :- " + (System.currentTimeMillis()-startTime) + ".ms");
    }


    public static void main(String args[]) throws IOException, ParseException {
        FSDirectoryExample fsDirectoryExample = new FSDirectoryExample();
        System.out.println("Writer Process start.....");
       fsDirectoryExample.writeIndex(Account.getAccount());
       System.out.println("Writer Process complete.....");
        // test case
        Query query1 = new TermQuery(new Term("phone", "(261)-455-8193"));
        Query query2 = new TermQuery(new Term("status", "false"));
        BooleanQuery query = new BooleanQuery.Builder().add(query1, BooleanClause.Occur.SHOULD).build();
        while (true) {
            fsDirectoryExample.searchIndex(query.toString());
        }

//        while (true) {
//            long startTime = System.nanoTime();
//            String keys[] = UUID.randomUUID().toString().split("-");
//            BooleanQuery booleanQuery = new BooleanQuery.Builder().build();
//            for (String key: keys) {
//                System.out.println("Search for ..." + key);
//                Query query = new TermQuery(new Term("id", key));
//                fsDirectoryExample.searchIndex("id", key, 15);
//            }
//        }

    }

}
