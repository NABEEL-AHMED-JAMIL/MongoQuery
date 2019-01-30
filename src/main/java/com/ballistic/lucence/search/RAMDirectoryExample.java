package com.ballistic.lucence.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class RAMDirectoryExample {

    private Analyzer analyzer;
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter writer;
    private IndexReader reader;

    public RAMDirectoryExample() {
        this.setAnalyzer(new StandardAnalyzer());
        this.setDirectory(new RAMDirectory());
    }

    private Analyzer getAnalyzer() { return analyzer; }
    private void setAnalyzer(Analyzer analyzer) { this.analyzer = analyzer; }

    private Directory getDirectory() { return directory; }
    private void setDirectory(Directory directory) { this.directory = directory; }

    private IndexWriterConfig getIndexWriterConfig() { return indexWriterConfig; }
    private void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) { this.indexWriterConfig = indexWriterConfig; }

    private IndexWriter getWriter() { return writer; }
    private void setWriter(IndexWriter writer) { this.writer = writer; }

    private IndexReader getReader() { return reader; }
    private void setReader(IndexReader reader) { this.reader = reader; }

    // value for store
    public void writerDocument() {
        AtomicLong startTime = new AtomicLong(System.nanoTime());
        try (Stream<String> stream = Files.lines(Paths.get("C://Users//AdMaxim//Desktop//MongoQuery//src//main//resources//data//record1.txt"))) {
            // IndexWriter Configuration
            this.setIndexWriterConfig(new IndexWriterConfig(this.getAnalyzer()));
            this.getIndexWriterConfig().setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            this.setWriter(new IndexWriter(this.getDirectory(), this.getIndexWriterConfig()));
            System.out.println("Writer Configuration time :- " + (System.nanoTime() - startTime.get()) + "non");
            AtomicReference<Integer> integer = new AtomicReference<>(0);
            stream.parallel().forEach(uuid -> {
                try {
                    Document document = new Document();
                    document.add(new TextField("uuid", uuid, Field.Store.YES));
                    System.out.println("ID :- " + integer.getAndSet(integer.get() + 1)  + " Document :- " + document.get("uuid"));
                    System.out.println("+------------------------------------------------------+");
                    this.getWriter().addDocument(document);

                } catch (IOException e) {
                    System.err.println("Error " + e.getMessage());
                }
            });
            System.out.println("Writer time :- " + (System.nanoTime() - startTime.get()) + "non");
            System.out.println("Field Name" + this.getWriter().getFieldNames().toString());
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }
    }

    public void readDocument(String queryStr) {
        try {
            this.setReader(DirectoryReader.open(getDirectory()));
            //Create index searcher
            IndexSearcher searcher = new IndexSearcher(this.getReader());
            //Build query
            try {
                QueryParser qparser = new QueryParser("uuid", this.getAnalyzer());
                Query query = qparser.parse(queryStr);
                //Search the index
                TopDocs foundDocs = searcher.search(query, 100);
                // Total found documents
                System.out.println("Total Results :: " + foundDocs.totalHits);
                //Let's print found doc names and their content along with score
                for (ScoreDoc sd : foundDocs.scoreDocs) {
                    Document d = searcher.doc(sd.doc);
                    System.out.println("Document Name | " + d.get("uuid") + "  :|: Score : " + sd.score);
                    System.out.println("+------------------------------------------------------------------------+");
                }
            } catch (ParseException e) {
                System.err.println("Error " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error " + e.getMessage());
        }

    }

    public void writerClose() throws IOException { this.getWriter().close(); }
    public void readerClose() throws IOException { this.getReader().close(); }

    public static void main(String args[]) throws IOException {

        RAMDirectoryExample ramDirectoryExample = new RAMDirectoryExample();
        System.out.println("Writer Process start.....");
        ramDirectoryExample.writerDocument();
        ramDirectoryExample.writerClose();
        System.out.println("Writer Process complete.....");
        while (true) {
            long startTime = System.nanoTime();
            System.out.println("Reader Process start..... " + startTime);
            String keys[] = UUID.randomUUID().toString().split("-");
            for (String key: keys) {
                System.out.println("Search for ..." + key);
                ramDirectoryExample.readDocument(key);
                ramDirectoryExample.readerClose();
            }
            System.out.println("Reader Process complete..... " + (System.nanoTime() - startTime));
        }
    }
}
