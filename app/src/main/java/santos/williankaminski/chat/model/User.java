package santos.williankaminski.chat.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import santos.williankaminski.chat.config.FirebaseConf;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class User {

    private String id;
    private String userName;
    private String userEmail;
    private String userPassword;

    public User() {

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void registerData(){

        DatabaseReference databaseReference = FirebaseConf.getFirenaseDatabase();
        DatabaseReference user = databaseReference.child("users").child(getId());

        user.setValue(this);
    }
}
