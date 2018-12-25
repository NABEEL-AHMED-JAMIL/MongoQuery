package com.ballistic.fire.service;

import com.ballistic.fire.config.FbService;
import com.ballistic.fire.pojo.User;
import com.google.firebase.auth.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import static com.ballistic.fire.pojo.User.fetchUsers;
// Sample :- https://github.com/firebase/quickstart-java/blob/master/auth/src/main/java/com/google/firebase/quickstart/AuthSnippets.java
public class FBAuthentication {

    private FbService fbService = null;
    private FirebaseAuth defaultAuth = null;
    private UserRecord userRecord = null;
    private UserRecord.CreateRequest createRequest = null;

    public enum UserFetchEnum {

        Email("EMAIL"), Phone("PHONE"), Uuid("UUID");

        private String	value;

        UserFetchEnum(String code) { this.value = code; }

        public String getValue( ) { return value; }

        @Override
        public String toString() { return "UserFetchEnum{" + "value='" + value + '\'' + '}'; }

    }

    /* *
     * @service :- Retrieve Auth-user data from the firebase
     * use case :- BY-EMAIL, BY-PHONE, BY-UUID
     * */
    public void retrieveUserData(String data, UserFetchEnum fetchType) {
        long startTime = System.currentTimeMillis();
        if(FbService.getInstance() != null && (data != null &&  fetchType != null)) {
            try {
                // get-ing default app
                this.defaultAuth = FirebaseAuth.getInstance(this.fbService.getDefaultApp());
                // find-option
                /**Note:- (If want to use the non Async method then other method)*/
                synchronized (FbService.class) {
                    switch (fetchType.getValue()) {
                        case "EMAIL":
                            // recode---fetch by email
                            //this.userRecord = this.defaultAuth.getUserByEmail(data);
                            this.userRecord = this.defaultAuth.getUserByEmailAsync(data).get();
                            break;
                        case "PHONE":
                            // recode---fetch by phone
                            //this.userRecord = this.defaultAuth.getUserByPhoneNumber(data);
                            this.userRecord = this.defaultAuth.getUserByPhoneNumberAsync(data).get();
                            break;
                        case "UUID":
                            // recode---fetch by uuid
                            //this.userRecord = this.defaultAuth.getUser(data);
                            this.userRecord = this.defaultAuth.getUserAsync(data).get();
                            break;
                    }
                }
                System.out.println("+-----------------------------------------");
                if(this.userRecord != null) {
                    User user = new User();
                    // do process
                    user.setId(this.userRecord.getUid());
                    // Display Uuid
                    System.out.println("Uuid :- " + user.getId());
                    // Display Email && Verified
                    if(this.userRecord.getEmail() != null) {
                        user.setEmail(this.userRecord.getEmail());
                        user.setVerified(this.userRecord.isEmailVerified());
                        System.out.println("Email :- " + user.getEmail() + " Verified :- " + user.getVerified());
                    }
                    // Display Phone Number
                    if (this.userRecord.getPhoneNumber() != null) {
                        user.setPhone(this.userRecord.getPhoneNumber());
                        System.out.println("Display Phone :- " + user.getPhone());
                    }
                    // Display Name
                    if(this.userRecord.getDisplayName() != null) {
                        user.setName(this.userRecord.getDisplayName());
                        System.out.println("Display Name :- " + user.getName());
                    }
                    // Display Photo
                    if(this.userRecord.getPhotoUrl() != null) {
                        user.setPhoto(this.userRecord.getPhotoUrl());
                        System.out.println("Display Photo :- " + user.getPhoto());
                    }
                } else {
                    // 0.0% => case chance of execution
                    System.out.println("Sorry Record Not Found...");
                }
            } catch (InterruptedException e) {
                System.err.println("Error :- " + e.getLocalizedMessage());
            }
            catch (ExecutionException e) {
                System.err.println("Error :- " + e.getLocalizedMessage());

            }
        }else {
            if(FbService.getInstance() == null) {
                System.out.println("Your Instance Not Create Yet....");
            }
            if (data == null) {
                System.out.println("Your Data Empty....");
            }
            if (fetchType == null) {
                System.out.println("Your Fetch Type Not Create Yet....");
            }
        }
        System.out.println("Fetch Time End Time :- " + (System.currentTimeMillis() - startTime) / 1000 + ".sec");
    }

    /**
     * Note :- Possible use case are bleow
     * 1) (anonymous) :- 1) user save all null and id' generate 2) phone number come under too
     * 2) (Password) :-  1) user password should be passwrod > 6
     * 3) (Email) :- valid email pattern should be use like :- "nabeel.amd93@gmail.com"
     * */
    public void saveUserDatas(List<User> users) {
        if(FbService.getInstance() !=  null) {
            // create the instance
            users.parallelStream().forEach(user -> {
                this.setCreateRequest(new UserRecord.CreateRequest());
                try {
                    StringBuilder error = new StringBuilder();
                    if(user.getEmail() != null) {
                        // done -> case email should valid pattern
                        if(Pattern.compile(".+@.+\\.[a-z]+").matcher(user.getEmail()).matches()) {
                            this.getCreateRequest().setEmail(user.getEmail());
                        }else {
                            // do-process
                            error.append("Email :- Not Valid " +  user.getEmail() + " || ");
                            System.err.println("Email Not Valid " +  user.getEmail());
                        }

                        if (user.getVerified() != null) {
                            this.getCreateRequest().setEmailVerified(user.getVerified());
                        } else {
                            this.getCreateRequest().setEmailVerified(false);
                        }

                        // done -> case password>6
                        if(user.getPassword() != null && user.getPassword().length() > 6) {
                            this.getCreateRequest().setPassword(user.getPassword());
                        }else {
                            // do-process
                            error.append("Password :- Password not Valid " + user.getPassword() + " || ");
                            System.err.println("Password not Valid " + user.getPassword());
                        }
                    }

                    // done -> case phone valid '+...00...0..0'
                    if(user.getPhone() != null) {
                        if(Pattern.compile("^(\\+\\d{1,3}[- ]?)?\\d{10}$").matcher(user.getPhone()).matches()) {
                            this.getCreateRequest().setPhoneNumber(user.getPhone());
                        }else {
                            // do process....
                            error.append("Phone :- Phone Number not Valid " +  user.getPhone() + " ");
                            System.err.println("Phone Number Not Valid " +  user.getPhone());
                        }
                    }

                    if(user.getName() != null) {
                        this.getCreateRequest().setDisplayName(user.getName());
                    }

                    // disabled mean block the user
                    if(user.getDisabled() != null) {
                        this.getCreateRequest().setDisabled(user.getDisabled());
                    }

                    if(user.getPhoto() != null) {
                        this.getCreateRequest().setPhotoUrl(user.getPhoto());
                    }
                    /**
                     * Note :- Show process..
                     * */
                    if(error.length() > 1) {
                        System.err.println(error.toString());
                    } else {
                        this.defaultAuth = FirebaseAuth.getInstance(this.fbService.getDefaultApp());
                        //this.userRecord = this.defaultAuth.createUser(this.getCreateRequest());
                        this.userRecord = this.defaultAuth.createUserAsync(this.getCreateRequest()).get();
                        System.out.println("Successfully created new user");
                    }
                    // response will add into the licence
                } catch (InterruptedException e) {
                    System.err.println("Error :- " + e.getLocalizedMessage());
                } catch (ExecutionException e) {
                    System.err.println("Error :- " + e.getLocalizedMessage());
                }
            });
        }else {
            System.out.println("Your Instance Not Create Yet....");
        }
    }

    public void showListUsers(String record) {
        if(FbService.getInstance() !=  null) {
            // Start listing users from the beginning, 1000 at a time.
            ListUsersPage page = null;
            try {
                this.defaultAuth = FirebaseAuth.getInstance(this.fbService.getDefaultApp());
                page = this.defaultAuth.listUsers(record);
                while (page != null) {
                    for (ExportedUserRecord user : page.getValues()) { System.out.println("User: " + user.getUid()); }
                    page = page.getNextPage();
                }
            } catch (FirebaseAuthException e) {
                System.err.println("Error :- " + e.getLocalizedMessage() + " Code :-  " + e.getErrorCode());
            }

        } else {
            System.out.println("Your Instance Not Create Yet....");
        }
    }

    public UserRecord.CreateRequest getCreateRequest() { return createRequest; }
    private void setCreateRequest(UserRecord.CreateRequest createRequest) { this.createRequest = createRequest; }

    public static void main(String args[]) {
        FBAuthentication authentication = new FBAuthentication();
        //authentication.saveUserDatas(fetchUsers());
        while (true) {
            authentication.retrieveUserData("nabeel.amd930@gmail.com", UserFetchEnum.Email);
            authentication.retrieveUserData("nabeel.amd93@gmail.com", UserFetchEnum.Email);
        }
    }

}
