package santos.williankaminski.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class CadastroActivity extends AppCompatActivity {

    private EditText editTextCadastroNome;
    private EditText editTextCadastroEmail;
    private EditText editTextCadastroSenha;
    private Button buttonCadastroCadastrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        initComponents();
        events();
    }

    public void initComponents(){
        editTextCadastroNome = findViewById(R.id.editTextCadastroNome);
        editTextCadastroEmail = findViewById(R.id.editTextCadastroEmail);
        editTextCadastroSenha = findViewById(R.id.editTextCadastroSenha);
        buttonCadastroCadastrar = findViewById(R.id.buttonCadastroCadastrar);
    }

    public void validateRegisterUser(){

        // Validar a consistencia dos dados
        String nome = editTextCadastroNome.getText().toString();
        String email = editTextCadastroEmail.getText().toString();
        String senha = editTextCadastroSenha.getText().toString();
        String msg = "";

        if(!nome.isEmpty()){ // valida nome
            if(!email.isEmpty()){ // valida email
                if(!senha.isEmpty()){ // valida senha

                    User user = new User();
                    user.setUserName(nome);
                    user.setUserEmail(email);
                    user.setUserPassword(senha);

                    registerUser(user);

                }else{
                    msg = "Preencha a senha!";
                }
            }else{
                msg = "Preencha o email!";
            }
        }else{
            msg = "Preencha o nome!";
        }

        if(!msg.isEmpty()){
            Toast.makeText(
                    this,
                    msg,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void registerUser(User user){

        auth = FirebaseConf.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                user.getUserEmail(),
                user.getUserPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(
                            getApplicationContext(),
                            "Usuário cadastrado com sucesso!",
                            Toast.LENGTH_SHORT
                    ).show();
                    finish();

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
                        msg = "Erro ao cadastrar usuário " + e.getMessage();
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

    public void events(){
        buttonCadastroCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateRegisterUser();
            }
        });
    }
}
