package com.ballistic.coredel;

import com.ballistic.util.LuceneToDocument;
import com.ballistic.util.LuceneToPojo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * 1) Search By Field
 * 2) Search By Range
 * 3) Search By WildCards (?=> single char, * => multiple char)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
public class LuceneDBContext {

    protected LuceneToDocument luceneToDocument;
    protected LuceneToPojo luceneToPojo;

    private String INDEX_DIR = "G://lucene";
    private Analyzer analyzer;
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private IndexWriter writer;
    private IndexReader reader;

    public LuceneDBContext() throws IOException {
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
    private void writerClose() throws IOException { this.getWriter().close(); }

    private IndexReader getReader() { return reader; }
    private void setReader(IndexReader reader) { this.reader = reader; }
    // will close
    private void readerClose() throws IOException { this.getReader().close(); }

    private IndexWriter createWriter() throws IOException {
        this.setIndexWriterConfig(new IndexWriterConfig(this.getAnalyzer()));
        this.setWriter(new IndexWriter(this.getDirectory(), this.getIndexWriterConfig()));
        return getWriter();
    }

    private IndexSearcher createSearcher() throws IOException {
        this.setReader(DirectoryReader.open(getDirectory()));
        return new IndexSearcher(this.getReader());
    }

    public LuceneToDocument getLuceneToDocument() { return luceneToDocument; }
    public LuceneToPojo getLuceneToPojo() { return luceneToPojo; }

    public void index(Document document) throws IOException {}

    public List<Document> executeQuery(Query query, Integer maxResults) throws IOException {
        return null;
    }

}
