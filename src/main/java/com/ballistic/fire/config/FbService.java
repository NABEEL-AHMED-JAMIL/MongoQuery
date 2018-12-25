package com.ballistic.fire.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

// Note:- This getInstance create on the run-time of the application in jvm right not we are not doing
public class FbService {

    private final static String path = "filestore-95f49-firebase-adminsdk-pq5wy-28e8da6e17.json";
    private final static String dbPath = "https://filestore-95f49.firebaseio.com";
    private final static String bucketPath = "filestore-95f49.appspot.com";

    private static FbService SINGLE_INSTANCE = null;
    private static FileInputStream serviceAccount = null;
    private static FirebaseOptions options = null;
    private static FirebaseApp defaultApp = null;
    private static FirebaseDatabase firebaseDatabase = null;

    private FbService() {}

    /**
     * Note:- Init the Firebase-App with the provided * Database * Store
     * */
    public static FbService getInstance() {
        long startTime = System.currentTimeMillis();
        if (SINGLE_INSTANCE == null) {
            synchronized (FbService.class) {
                //if (SINGLE_INSTANCE == null) {
                System.out.println("INSTANCE :- CREATING.");
                SINGLE_INSTANCE = new FbService();
                try {
                    serviceAccount = new FileInputStream(ClassLoader.getSystemResource(path).getPath());
                    options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).
                            setDatabaseUrl(dbPath).setStorageBucket(bucketPath).build();
                    defaultApp = FirebaseApp.initializeApp(options);
                    //firebaseDatabase = FirebaseDatabase.getInstance(defaultApp);
                    dbRefRulesInit(defaultApp);
                } catch (FileNotFoundException e) {
                    System.err.println("Error :- " +  e.getMessage());
                } catch (IOException e) {
                    System.err.println("Error :- " +  e.getMessage());
                }
                //}
            }
        }
        System.out.println("Instance End Time :- " + (System.currentTimeMillis() - startTime) + ".ms");
        return SINGLE_INSTANCE;
    }

    /**
     * Note :- Method not usefull for this application demo
     * while contain in case of next time use
     * * */
    private static void dbRefRulesInit(FirebaseApp defaultApp) {
        // As an admin, the app has access to read and write all data, regardless of Security Rules
        firebaseDatabase = FirebaseDatabase.getInstance(defaultApp);
        DatabaseReference databaseReference = firebaseDatabase.getReference("/documents");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println("Document " + document);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error" + error.getMessage());
            }
        });
    }


    public static FirebaseApp getDefaultApp() { return defaultApp; }
    public static FirebaseDatabase getFirebaseDatabase() { return firebaseDatabase; }

    public static void main(String args[]) {
        //while (true) {
            FbService service = getInstance();
            System.out.println(service.getDefaultApp().getName());
        //}
    }
}
