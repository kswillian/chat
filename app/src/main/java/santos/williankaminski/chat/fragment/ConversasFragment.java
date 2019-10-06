package santos.williankaminski.chat.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.activity.ChatActivity;
import santos.williankaminski.chat.adapter.ContatosAdaper;
import santos.williankaminski.chat.adapter.ConversasAdapter;
import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.model.Talk;
import santos.williankaminski.chat.model.User;
import santos.williankaminski.chat.util.RecyclerItemClickListener;
import santos.williankaminski.chat.util.UserFirebase;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ConversasFragment extends Fragment {

    private RecyclerView recyclerViewConversas;
    private List<Talk> talkList = new ArrayList<>();
    private ConversasAdapter adapter;

    private DatabaseReference databaseReference;
    private DatabaseReference talkRef;
    private ChildEventListener childEventListenerConversas;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewConversas = view.findViewById(R.id.recyclerViewConversas);

        //Configurar o Adapter
        adapter = new ConversasAdapter(talkList, getActivity());

        //Configurar o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter(adapter);

        //Configurar o evento de Click
        recyclerViewConversas.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerViewConversas,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Talk talk = talkList.get(position);

                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("chatContato", talk.getUser());
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        //Configura conversas Referece
        String idUser = UserFirebase.getUserId();
        databaseReference = FirebaseConf.getFirenaseDatabase();
        talkRef = databaseReference.child("talks").child(idUser);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarConversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        talkRef.removeEventListener(childEventListenerConversas);
    }

    public void recuperarConversas(){

        childEventListenerConversas = talkRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                // Recuperar Conversa
                Talk talk = dataSnapshot.getValue(Talk.class);
                talkList.add(talk);
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
