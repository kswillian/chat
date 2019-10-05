package santos.williankaminski.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.model.Permission;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ConfiguracoesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        initComponents();
        confgToolbar();

        // Validar as permissões
        Permission.validatePermission(permissions, this, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissionResult: grantResults){
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                alertValidate();
            }
        }
    }

    public void alertValidate(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.permission_alert_title));
        builder.setMessage(getResources().getString(R.string.permission_alert_message));
        builder.setCancelable(false);
        builder.setPositiveButton(
                getResources().getString(R.string.permission_alert_confirm),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
        });

        builder.create().show();

    }

    public void initComponents(){
        toolbar = findViewById(R.id.toolbarPrincipal);
    }

    public void confgToolbar(){
        toolbar.setTitle(getResources().getString(R.string.toolbar_confg));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
