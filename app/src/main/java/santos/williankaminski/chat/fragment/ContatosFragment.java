package santos.williankaminski.chat.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.activity.ChatActivity;
import santos.williankaminski.chat.adapter.ContatosAdaper;
import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.model.User;
import santos.williankaminski.chat.util.RecyclerItemClickListener;
import santos.williankaminski.chat.util.UserFirebase;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ContatosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ContatosAdaper adaper;
    private DatabaseReference userRef;
    private ValueEventListener valueEventListener;

    private FirebaseUser firebaseUser;

    private ArrayList<User> listContatos = new ArrayList<>();


    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_contatos, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        firebaseUser = UserFirebase.getUser();

        // Configurar o Adapter
        adaper = new ContatosAdaper(listContatos, getActivity());

        // Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adaper);

        // Configurando o evento de click no recyclerview
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ChatActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
                )
        );

        userRef = FirebaseConf.getFirenaseDatabase().child("users");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarContatos();
    }

    @Override
    public void onStop() {
        super.onStop();
        userRef.removeEventListener(valueEventListener);
    }

    public void recuperarContatos(){

        valueEventListener  = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    User user = dados.getValue(User.class);
                    if(!firebaseUser.getEmail().equals(user.getUserEmail())){
                        listContatos.add(user);
                    }
                }

                adaper.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
