package santos.williankaminski.chat.util;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.model.User;

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

    public static FirebaseUser getUser(){
        FirebaseAuth user = FirebaseConf.getFirebaseAuth();
        return user.getCurrentUser();
    }

    public static boolean uploadUserPhoto(Uri url){

        try {
            FirebaseUser user = getUser();

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();

            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.i("Perfil", "Erro ao atualizar foto de perfil.");
                    }
                }
            });

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean uploadUserName(String name){

        try {
            FirebaseUser user = getUser();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.i("Perfil", "Erro ao atualizar nome de perfil.");
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static User getUserLogin(){

        FirebaseUser firebaseUser = getUser();

        User user = new User();
        user.setUserName(firebaseUser.getEmail());
        user.setUserName(firebaseUser.getDisplayName());

        if(firebaseUser.getPhotoUrl() == null){
            user.setUserPhoto("");
        }else{
            user.setUserPhoto(firebaseUser.getPhotoUrl().toString());
        }

        return user;
    }
}
