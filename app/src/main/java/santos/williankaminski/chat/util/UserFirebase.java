package santos.williankaminski.chat.util;

import com.google.firebase.auth.FirebaseAuth;

import santos.williankaminski.chat.config.FirebaseConf;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class UserFirebase {

    public static String getUserId(){

        FirebaseAuth user = FirebaseConf.getFirebaseAuth();
        String email = user.getCurrentUser().getEmail();
        String idUser = Base64Custom.encodeBase64(email);
        return idUser;
    }

}
