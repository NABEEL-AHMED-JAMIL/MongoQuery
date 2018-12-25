package com.ballistic.fire.service;

import com.ballistic.fire.config.FbService;
import com.google.cloud.storage.Bucket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;

import java.util.Arrays;

public class FBFileStore {

    private static final String FOLDER_NAME[] = {"video", "text", "image"};
    private static volatile Boolean isFileInitialized = false;
    private FbService fbService = null;
    private FirebaseAuth defaultAuth = null;
    private Bucket bucket = null;

    public FBFileStore() {
        if(!isFileInitialized) {
            Arrays.stream(FOLDER_NAME).parallel().forEach(folder -> {
                if(!this.isFolderContain(folder)) {
                    // while
                }
            });
            isFileInitialized = true;
        }
    }

    public static void createFbStoreFolder() {}

    public static void storeFileToFireStore() {

    }

    public static void downloadFileFromFireStore() {

    }

    public Boolean isFolderContain(String folderName) {
        return null;
    }

}
