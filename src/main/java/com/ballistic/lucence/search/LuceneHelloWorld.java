package com.ballistic.lucence.search;

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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;

public class LuceneHelloWorld {

    public static void test1() throws IOException, ParseException {
        StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        // store => hai
        Directory directory = new RAMDirectory();
        IndexWriterConfig writerConfig = new IndexWriterConfig(standardAnalyzer);
        // writer for store help to write the index
        IndexWriter writer = new IndexWriter(directory, writerConfig);
        // Object => Document
        Document document = new Document();
        // In-real world example content would be the actual content that needs to be indexed.
        document.add(new TextField("content", "Hello World", Field.Store.YES));
        writer.addDocument(document);
        document.add(new TextField("content", "Hello People", Field.Store.YES));
        writer.addDocument(document);
        document.add(new TextField("content", "Hello Pakistan", Field.Store.YES));
        writer.addDocument(document);
        writer.close();
        System.out.println("Document" + document.toString());

        // Now let's try to Search for Hello
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);
        QueryParser parser = new QueryParser("content", standardAnalyzer);
        Query query = parser.parse("Hello");
        TopDocs results = searcher.search(query, 5);
        System.out.println("Hits for Hello ---> " + results.totalHits);

        // Case Insensitive Search
        query = parser.parse("hello");
        results = searcher.search(query, 5);
        System.out.println("Hits for hello ---> " + results.totalHits);

        // search for a value not indexed
        query = parser.parse("Hi there");
        results = searcher.search(query, 5);
        System.out.println("Hits for Hi there ---> " + results.totalHits);


    }

    public static void main(String args[]) throws IOException, ParseException {

        test1();

    }
}
