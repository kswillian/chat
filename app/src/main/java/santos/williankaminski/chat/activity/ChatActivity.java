package santos.williankaminski.chat.activity;

import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import santos.williankaminski.chat.R;
import santos.williankaminski.chat.adapter.MensagensAdapter;
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
    private RecyclerView recyclerMensagens;

    private User userAddress;
    private MensagensAdapter adapter;
    private List<Message> messages = new ArrayList<>();

    private String idUserSender;
    private String idUserAddress;

    private DatabaseReference databaseReference;
    private DatabaseReference messageRef;
    private ChildEventListener childEventListenerMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initComponents();
        initToolBar();
        getUserSelected();

        recoverUsers();
        initRecyclerView();

        databaseReference = FirebaseConf.getFirenaseDatabase();
        messageRef = databaseReference
                .child("messages")
                .child(idUserSender)
                .child(idUserAddress);

        events();
    }

    @Override
    protected void onStart() {
        super.onStart();
        recoverMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();

        messageRef.removeEventListener(childEventListenerMessages);
    }

    public void initComponents(){
        circleImageFotoChat = findViewById(R.id.circleImageFotoChat);
        textViewNomeChat = findViewById(R.id.textViewNomeChat);
        editTextMensagem = findViewById(R.id.editTextMensagem);
        fabEnviar = findViewById(R.id.fabEnviar);
        recyclerMensagens = findViewById(R.id.recyclerMensagens);
    }

    public void initToolBar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initRecyclerView(){

        //Configurando o Adapter
        adapter = new MensagensAdapter(messages, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerMensagens.setLayoutManager(layoutManager);
        recyclerMensagens.setHasFixedSize(true);
        recyclerMensagens.setAdapter(adapter);

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

                    //Salvando a mensagem para o remetente
                    saveMessage(idUserSender, idUserAddress, message);

                    //Salvando a mensagem para o destinatario
                    saveMessage(idUserAddress, idUserSender, message);

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

    private void recoverMessage(){

        childEventListenerMessages = messageRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message message = dataSnapshot.getValue(Message.class);
                messages.add(message);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
