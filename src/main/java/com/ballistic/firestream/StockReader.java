package com.ballistic.firestream;

import com.ballistic.firestream.db.DbConnection;
import com.ballistic.firestream.db.StockPriceDAB;
import com.ballistic.firestream.db.StockPriceManager;
import com.ballistic.firestream.pojo.StockPrice;
import com.ballistic.firestream.pojo.dto.FieldQuery;
import com.ballistic.firestream.search.LuceneEngine;
import com.ballistic.firestream.search.LuceneEngineManager;
import com.ballistic.firestream.util.StockPriceUtil;
import org.apache.lucene.document.Document;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StockReader {

    private String downloadUrl = "https://raw.githubusercontent.com/rrohitramsen/firehose/master/src/main/resources/data/stock_6_lac.csv";

    private StockPriceManager stockPriceManager;
    private LuceneEngineManager luceneEngineManager;

    public StockReader() { }

    public StockReader(StockPriceManager stockPriceManager) {
        this.stockPriceManager = stockPriceManager;
    }

    public StockReader(LuceneEngineManager luceneEngineManager) {
        this.luceneEngineManager = luceneEngineManager;
    }

    public StockReader(StockPriceManager stockPriceManager, LuceneEngineManager luceneEngineManager) {
        this.stockPriceManager = stockPriceManager;
        this.luceneEngineManager = luceneEngineManager;
    }

    public StockPriceManager getStockPriceManager() { return stockPriceManager; }
    public void setStockPriceManager(StockPriceManager stockPriceManager) { this.stockPriceManager = stockPriceManager; }

    public LuceneEngineManager getLuceneEngineManager() { return luceneEngineManager; }
    public void setLuceneEngineManager(LuceneEngineManager luceneEngineManager) { this.luceneEngineManager = luceneEngineManager; }

    public void openConnection() {
        try {
            long startTime = System.currentTimeMillis();
            URL myUrl = new URL(downloadUrl);
            HttpsURLConnection conn = (HttpsURLConnection) myUrl.openConnection();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

            List<StockPrice> stockPricesPojos = new ArrayList<>();
            List<Document> stockPricesDocumenteds = new ArrayList<>();
            String inputLine;

            Integer counter = 0;
            Integer currentLine = 0;
            Integer loop = 0;
            while ((inputLine = br.readLine()) != null) {
                System.out.println("Current Line :- " + (currentLine) + " Value :- " + inputLine);
                if(currentLine != 0) {
                    System.out.println("+---------------------------------------------------------------------------------------------------------+");
                    StockPrice stockPrice = StockPriceUtil.rawDataToPojo(inputLine);
                    Document stockPriceDocument =StockPriceUtil.stockPriceToDocument(stockPrice);
                    System.out.println("+---------------------------------------------------------------------------------------------------------+");
                    stockPricesPojos.add(stockPrice);
                    stockPricesDocumenteds.add(stockPriceDocument);
                    if(counter == 15000) {
                        System.out.println("Loop :- " + loop++  + "  Counter :- " + counter + " Total Fetch :- " + (loop * counter));
                        if(this.stockPriceManager != null) {
                            this.stockPriceManager.save(stockPricesPojos);
                            stockPricesPojos = new ArrayList<>();
                        }
                        if(this.luceneEngineManager != null) {
                            this.luceneEngineManager.save(stockPricesDocumenteds);
                            stockPricesDocumenteds = new ArrayList<>();
                        }
                        System.out.println("+---------------------------------------------------------------------------------------------------------+");
                        counter = 0;
                        //break;
                    }
                }
                currentLine++; counter++;
                //Thread.sleep(200);
            }
            br.close();
            System.out.println("Start Time :- " + new Date(startTime) +
                ", Current Time :- " + new Date(System.currentTimeMillis()) +
                "Total Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        } catch (IOException ex) {
            System.out.println("Error :- " + ex.getLocalizedMessage());
        } catch (NullPointerException ex) {
            System.out.println("Error :- " + ex.getLocalizedMessage());
        }/* catch (InterruptedException ex) {
         *    System.out.println("Error :- " + ex.getLocalizedMessage());
         * }
         * */
    }

    public List<FieldQuery> getQuery() {
        // Note 5 operation out form the method no need
        List<FieldQuery> query = new ArrayList<>();
        query.add(new FieldQuery("openPrice", 1 , 47.25, "Double"));
        query.add(new FieldQuery("openPrice", 1 , 47.65, "Double"));
        return query;
    }


    public static void main(String args[]) {
        try {
            // new StockPriceManager(new StockPriceDAB(new DbConnection("localhost:27017", "stock"))),
            StockReader stockReader = new StockReader(
                    new StockPriceManager(new StockPriceDAB(new DbConnection("localhost:27017", "stock"))),new LuceneEngineManager(new LuceneEngine()));
            //stockReader.openConnection();
            while (true) {
                //System.out.println("Result :- " +
                        stockReader.getLuceneEngineManager().findById(new FieldQuery("uuid", 0 , "f5a365daa4354f878274be7e0b478e5b", "String"));
                //);
            }

            /**
             *  StockReader stockPrice = new StockReader(new StockPriceManager(new StockPriceDAB(new DbConnection("localhost:27017", "stock"))));
             *  //stockPrice.openConnection();
             *  stockPrice.getStockPriceManager().getStockPricesByFields(null);
             *  System.out.println(stockPrice.getStockPriceManager().getStockPricesByFields(null));
             *  stockPrice.openConnection()
             * */
        }catch (IOException ex) {
            System.out.println("Error :- " + ex.getLocalizedMessage());
        } catch (NullPointerException ex) {
            System.out.println("Error :- " + ex.getLocalizedMessage());
        }

    }
}
