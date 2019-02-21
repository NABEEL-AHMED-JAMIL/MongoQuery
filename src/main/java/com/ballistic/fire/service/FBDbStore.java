package com.ballistic.fire.service;

import com.ballistic.fire.config.FbService;
import com.ballistic.fire.pojo.User;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FBDbStore {

    private FirebaseDatabase database = null;

    /**
     * Note:- saveData work as unique method for all table in db only single tree structure
     * */
    public void saveData(String collection, String document ,String value) {
        // condition send call-for authenticated first
        if(FbService.getInstance() !=  null) {
            this.database = FbService.getFirebaseDatabase();
            try {
                DatabaseReference accountRef = this.database.getReference().child(collection);
                ApiFuture<Void> saveUser = accountRef.setValueAsync(new User("nabeel.*******@gmail.com", true, "ballistic", "+923153817177", "util", "zyz", true));
                saveUser.get(10, TimeUnit.SECONDS);
                System.out.println("Account save");
            } catch (InterruptedException e) {
                System.out.println("Error " + e.getLocalizedMessage());
            } catch (ExecutionException e) {
                System.out.println("Error " + e.getLocalizedMessage());
            } catch (TimeoutException e) {
                System.out.println("Error " + e.getLocalizedMessage());
            }
            System.out.println("Value Store .... Check...");
        } else {
            System.out.println("Your Instance Not Create Yet....");
        }
    }

    public static void main(String args[]) {
        FBDbStore fbDbStore = new FBDbStore();
        fbDbStore.saveData("account", null, "pakistan");
    }

}
