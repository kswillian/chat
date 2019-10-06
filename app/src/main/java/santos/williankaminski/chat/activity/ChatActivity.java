package santos.williankaminski.chat.activity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import santos.williankaminski.chat.R;
import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.model.Message;
import santos.williankaminski.chat.model.User;
import santos.williankaminski.chat.util.Base64Custom;
import santos.williankaminski.chat.util.DateTime;
import santos.williankaminski.chat.util.UserFirebase;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ChatActivity extends AppCompatActivity {

    private CircleImageView circleImageFotoChat;
    private TextView textViewNomeChat;
    private EditText editTextMensagem;
    private FloatingActionButton fabEnviar;

    private User userAddress;

    private String idUserSender;
    private String idUserAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initComponents();
        initToolBar();
        getUserSelected();

        recoverUsers();

        events();
    }

    public void initComponents(){
        circleImageFotoChat = findViewById(R.id.circleImageFotoChat);
        textViewNomeChat = findViewById(R.id.textViewNomeChat);
        editTextMensagem = findViewById(R.id.editTextMensagem);
        fabEnviar = findViewById(R.id.fabEnviar);
    }

    public void initToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getUserSelected(){

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            userAddress = (User) bundle.getSerializable("chatContato");
            textViewNomeChat.setText(userAddress.getUserName());

            String foto = userAddress.getUserPhoto();

            if(foto != null){
                Uri url = Uri.parse(foto);
                Glide.with(this)
                        .load(url)
                        .into(circleImageFotoChat);
            }else{
                circleImageFotoChat.setImageResource(R.drawable.default_img);
            }
        }
    }

    public void recoverUsers(){
        idUserSender = UserFirebase.getUserId();
        idUserAddress = Base64Custom.encodeBase64(userAddress.getUserEmail());
    }

    public void events(){

        fabEnviar.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                String msg = editTextMensagem.getText().toString();

                if(!msg.isEmpty()){

                    Message message  = new Message();
                    message.setIdUser(idUserSender);
                    message.setMessage(msg);
                    message.setDate(DateTime.getTodayDateTime());
                    message.setStatus("new");

                    //Salvando a mensagem
                    saveMessage(idUserSender, idUserAddress, message);

                }else {
                    Toast.makeText(
                            getApplicationContext(),
                            getResources().getString(R.string.msg_empty),
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    public void saveMessage(String sender, String address, Message message){

        DatabaseReference databaseReference = FirebaseConf.getFirenaseDatabase();
        DatabaseReference messageRef = databaseReference.child("messages");

        messageRef.child(sender).child(address).push().setValue(message);

        editTextMensagem.setText(null);
    }
}
