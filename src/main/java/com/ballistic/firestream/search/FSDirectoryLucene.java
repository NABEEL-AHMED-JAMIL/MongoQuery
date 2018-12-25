package com.ballistic.firestream.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class FSDirectoryLucene {

    private static final String INDEX_DIR = "G://lucene";
    private Analyzer analyzer;
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter writer;
    private IndexReader reader;

    public FSDirectoryLucene() throws IOException {
        this.setAnalyzer(new StandardAnalyzer());
        this.setDirectory(org.apache.lucene.store.FSDirectory.open(Paths.get(INDEX_DIR)));
    }

    public FSDirectoryLucene(String INDEX_DIR) throws IOException {
        this.setAnalyzer(new StandardAnalyzer());
        this.setDirectory(org.apache.lucene.store.FSDirectory.open(Paths.get(INDEX_DIR)));
    }

    public Analyzer getAnalyzer() { return analyzer; }
    private void setAnalyzer(Analyzer analyzer) { this.analyzer = analyzer; }

    public Directory getDirectory() { return directory; }
    private void setDirectory(Directory directory) { this.directory = directory; }

    public IndexWriterConfig getIndexWriterConfig() { return indexWriterConfig; }
    private void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) { this.indexWriterConfig = indexWriterConfig; }

    public IndexWriter getWriter() { return writer; }
    private void setWriter(IndexWriter writer) { this.writer = writer; }
    //public void writerClose() throws IOException { this.getWriter().close(); }

    public IndexReader getReader() { return reader; }
    private void setReader(IndexReader reader) { this.reader = reader; }
    //public void readerClose() throws IOException { this.getReader().close(); }

    public IndexWriter createWriter() throws IOException {
        this.setIndexWriterConfig(new IndexWriterConfig(this.getAnalyzer()));
        this.setWriter(new IndexWriter(this.getDirectory(), this.getIndexWriterConfig()));
        return getWriter();
    }

    public IndexSearcher createSearcher() throws IOException {
        this.setReader(DirectoryReader.open(getDirectory()));
        return new IndexSearcher(this.getReader());
    }

}
