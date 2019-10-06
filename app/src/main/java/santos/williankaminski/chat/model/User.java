package santos.williankaminski.chat.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.util.UserFirebase;

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
    private String userPhoto;

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

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void registerData(){

        DatabaseReference databaseReference = FirebaseConf.getFirenaseDatabase();
        DatabaseReference user = databaseReference.child("users").child(getId());

        user.setValue(this);
    }

    public void updateData(){

        String idUser = UserFirebase.getUserId();
        DatabaseReference baseDatabase = FirebaseConf.getFirenaseDatabase();

        DatabaseReference userRef = baseDatabase.
                child("users").
                child(idUser);


        Map<String, Object> valuesUser = convertToMap();
        userRef.updateChildren(valuesUser);
    }

    @Exclude
    public Map<String, Object> convertToMap(){

        HashMap<String, Object> userMap = new HashMap<>();
        //userMap.put("userEmail", getUserEmail());
        userMap.put("userName", getUserName());
        userMap.put("userPhoto", getUserPhoto());
        //userMap.put("userPassword", getUserPassword());

        return userMap;
    }
}
