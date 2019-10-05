package santos.williankaminski.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.model.User;

/**
 * @author Willian Kaminski dos santos
 * @since 04-10-2019
 * @version 0.0.1
 */
public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginEmail;
    private EditText editTextLoginSenha;
    private Button buttonLoginCadastrar;
    private TextView textViewLoginCadastrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
        events();
    }

    public void initComponents(){
        editTextLoginEmail = findViewById(R.id.editTextLoginEmail);
        editTextLoginSenha = findViewById(R.id.editTextLoginSenha);
        textViewLoginCadastrar = findViewById(R.id.textViewLoginCadastrar);
        buttonLoginCadastrar = findViewById(R.id.buttonLoginCadastrar);
    }

    public void events(){
        openRegister();
        login();
    }

    public void openRegister(){

        textViewLoginCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
            }
        });
    }

    public void login(){

        buttonLoginCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateLoginUser();
            }
        });
    }

    public void validateLoginUser(){

        String email = editTextLoginEmail.getText().toString();
        String senha = editTextLoginSenha.getText().toString();
        String msg = "";

        if(!email.isEmpty()){ // valida email
            if(!senha.isEmpty()){ // valida senha

                User user = new User();
                user.setUserEmail(email);
                user.setUserPassword(senha);
                loginUser(user);

            }else{
                msg = "Preencha a senha!";
            }
        }else{
            msg = "Preencha o email!";
        }

        if(!msg.isEmpty()){
            Toast.makeText(
                    this,
                    msg,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void loginUser(User user){

        auth = FirebaseConf.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                user.getUserEmail(),
                user.getUserPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                }else{

                    String msg = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        msg = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        msg = "Por favor, digite uma e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e) {
                        msg = "Esta conta já foi cadastrada";
                    }catch (Exception e) {
                        msg = "Erro ao realizar a autenticação!";
                        e.printStackTrace();
                    }

                    if(!msg.isEmpty()){
                        Toast.makeText(
                                getApplicationContext(),
                                msg,
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
            }
        });
    }
}
