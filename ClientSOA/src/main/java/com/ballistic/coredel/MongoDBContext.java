package com.ballistic.coredel;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class MongoDBContext {

    private Datastore datastore;
    private MongoClient mongoClient;

    public MongoDBContext(String dbNames, String hosts) throws Exception {
        System.out.println(">============Start DB Connection============<");
        MongoClientOptions options = MongoClientOptions.builder()
            .connectionsPerHost(2).connectTimeout(60000).socketTimeout(60000)
            .maxWaitTime(1000).maxConnectionIdleTime(60000)
            .addCommandListener(new MongoCmListener())
            .addClusterListener (new MongoClusterListener(ReadPreference.secondary()))
            .readPreference(ReadPreference.secondaryPreferred())
            .build();
        try {
            this.mongoClient = new MongoClient(hosts, options);
            Morphia morphia= new Morphia();
            this.datastore = morphia.createDatastore(this.mongoClient, dbNames);
            this.datastore.ensureIndexes();
            System.out.println("DB:- Database connection Successful");
        } catch (Exception ex) {
            System.err.println("DB:- Database connection Fail");
            System.err.println("Error Message:- " + ex.getLocalizedMessage());
            throw ex;
        }
    }

    public Datastore getDatastore() { return datastore; }

}