package santos.williankaminski.chat.model;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class Permission {

    public static boolean validatePermission(String[] permissions, Activity activity, int requestCode){

        if(Build.VERSION.SDK_INT >= 23){

            List<String> listPermission = new ArrayList<>();

            /**
             * Percorre as permissões passadas,
             * verificando um a um se já tem
             * a permissão liberada
             */
            for (String permission: permissions){
                Boolean temPermissao = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if(!temPermissao){
                    listPermission.add(permission);
                }
            }

            if(listPermission.isEmpty()){
                return true;
            }else {
                String[] newPermission = new String[listPermission.size()];
                listPermission.toArray(newPermission);

                // Solicita permissão
                ActivityCompat.requestPermissions(activity, newPermission, requestCode);
            }
        }
        return true;
    }
}
