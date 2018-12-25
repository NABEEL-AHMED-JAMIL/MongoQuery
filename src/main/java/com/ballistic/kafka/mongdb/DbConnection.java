package com.ballistic.kafka.mongdb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

public class DbConnection {

    private Morphia morphia = null;
    private Datastore datastore = null;
    private MongoClient mongoClient = null;
    private String dbNames = null;
    private String hosts = null;

    private List<DbConnection> dbConnections = new ArrayList<>();


    public DbConnection(String hosts, String dbNames) {

        this.hosts = hosts;
        this.dbNames = dbNames;

        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(2).socketKeepAlive(true).connectTimeout(60000)
                .socketTimeout(60000).maxWaitTime(1000).maxConnectionIdleTime(60000)
                .readPreference(ReadPreference.secondaryPreferred())
                .build();

        // use :- hard-code for now
        if(this.hosts.equals("localhost:27017") && (this.dbNames != null && this.hosts != null)) {
            this.mongoClient = new MongoClient(hosts,options);
            this.morphia = new Morphia();
            this.datastore = this.morphia.createDatastore(this.mongoClient, dbNames);
            this.datastore.ensureIndexes();
            dbConnections.add(this);
            System.out.println("DB:- Database connection Successful");
        }else {
            System.err.println("DB:- Database connection Fail");
        }
    }

    public Datastore getDb() { return this.datastore; }
    public List<DbConnection> getDbConnections() { return dbConnections; }

    public static void main(String args[]) {

        //DbConnection dbConnection = new DbConnection("localhost:27017", "kdb");
    }

}
