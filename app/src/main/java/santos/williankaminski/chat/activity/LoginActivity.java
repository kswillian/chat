package santos.williankaminski.chat.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import santos.williankaminski.chat.R;

/**
 * @author Willian Kaminski dos santos
 * @since 04-10-2019
 * @version 0.0.1
 */
public class LoginActivity extends AppCompatActivity {

    private TextView textViewLoginCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        events();
    }

    public void initComponents(){
        textViewLoginCadastrar = findViewById(R.id.textViewLoginCadastrar);
    }

    public void events(){
        openRegister();
    }

    public void openRegister(){

        textViewLoginCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
            }
        });
    }
}
