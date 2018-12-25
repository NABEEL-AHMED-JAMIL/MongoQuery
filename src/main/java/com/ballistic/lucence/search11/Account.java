package com.ballistic.lucence.search11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//http://dius.github.io/java-faker/
public class Account {

    private String id;
    private String username;
    private String password;
    private Boolean status;
    private String phone;

    public Account() { }

    public Account(String id, String username, String password, Boolean status, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.phone = phone;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public static List<Account> getAccount() {

        List<Account> accounts = new ArrayList<>();

        for(int i=0; i<100; i++) {
            accounts.add(new Account(String.valueOf(UUID.randomUUID()),
                String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", ""),
                String.valueOf(UUID.randomUUID()).replaceAll("[^a-z]", ""),
                i%2 == 0 ? true :false, generatePhone()));
        }
        return accounts;
    }

    public static String generatePhone() {
        int num1, num2, num3; //3 numbers in area code
        int set2, set3; //sequence 2 and 3 of the phone number

        Random generator = new Random();

        //Area code number; Will not print 8 or 9
        num1 = generator.nextInt(7) + 1; //add 1 so there is no 0 to begin
        num2 = generator.nextInt(8); //randomize to 8 becuase 0 counts as a number in the generator
        num3 = generator.nextInt(8);

        // Sequence two of phone number
        // the plus 100 is so there will always be a 3 digit number
        // randomize to 643 because 0 starts the first placement so if i randomized up to 642 it would only go up yo 641 plus 100
        // and i used 643 so when it adds 100 it will not succeed 742
        set2 = generator.nextInt(643) + 100;

        //Sequence 3 of numebr
        // add 1000 so there will always be 4 numbers
        //8999 so it wont succed 9999 when the 1000 is added
        set3 = generator.nextInt(8999) + 1000;

        return  "(" + num1 + "" + num2 + "" + num3 + ")" + "-" + set2 + "-" + set3;

    }


    @Override
    public String toString() { return "Account{" + "id='" + id + '\'' + ", username='" + username + '\'' + ", password='" + password + '\'' + ", status=" + status + ", phone='" + phone + '\'' + '}'; }

}
