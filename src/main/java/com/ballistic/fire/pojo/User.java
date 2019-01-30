package com.ballistic.fire.pojo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String email;
    private Boolean verified;
    private String password;
    private String phone;
    private String name;
    private String photo;
    private Boolean disabled;

    public User() { }

    public User(String email, Boolean verified, String password, String phone, String name, String photo, Boolean disabled) {
        this.email = email;
        this.verified = verified;
        this.password = password;
        this.phone = phone;
        this.name = name;
        this.photo = photo;
        this.disabled = disabled;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getVerified() { return verified; }
    public void setVerified(Boolean verified) { this.verified = verified; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public Boolean getDisabled() { return disabled; }
    public void setDisabled(Boolean disabled) { this.disabled = disabled; }

    // fetch data from csv-file and save into
    public static List<User> fetchUsers() {
        ArrayList<User> users = new ArrayList<>();
        String csvFile = "user.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(ClassLoader.getSystemResource(csvFile).getPath()));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] user = line.split(cvsSplitBy);
                users.add(new User(user[0],Boolean.valueOf(user[1]),user[2],user[3],user[4],user[5],Boolean.valueOf(user[6])));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error :- " + e.getLocalizedMessage());
        } catch (IOException e) {
            System.err.println("Error :- " + e.getLocalizedMessage());
        } finally {
            if (br != null) {
                try { br.close(); }
                catch (IOException e) { e.printStackTrace(); }
            }
        }
        return users;
    }


    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", email='" + email + '\'' + ", verified=" + verified +
                ", password='" + password + '\'' + ", phone='" + phone + '\'' + ", name='" + name + '\'' +
                ", photo='" + photo + '\'' + ", disabled=" + disabled + '}';
    }
}
