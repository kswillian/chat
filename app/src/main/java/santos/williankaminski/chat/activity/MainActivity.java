package santos.williankaminski.chat.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import santos.williankaminski.chat.R;
import santos.williankaminski.chat.config.FirebaseConf;
import santos.williankaminski.chat.fragment.ContatosFragment;
import santos.williankaminski.chat.fragment.ConversasFragment;

/**
 * @author Willian Kaminski dos santos
 * @since 04-10-2019
 * @version 0.0.1
 */
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        confgToolbar();
        configureTab();

        auth = FirebaseConf.getFirebaseAuth();
    }

    public void initComponents(){
        toolbar = findViewById(R.id.toolbarPrincipal);
        viewPager = findViewById(R.id.viewPager);
        smartTabLayout = findViewById(R.id.viewPagerTab);
    }

    public void confgToolbar(){
        toolbar.setTitle(getResources().getString(R.string.toolbar_main));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuConfig:
                startActivity(new Intent(this, ConfiguracoesActivity.class));
                break;
            case R.id.menuSair:
                signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut(){
        try{
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void configureTab(){

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(getApplicationContext())
                        .add(getResources().getString(R.string.fragment_conversas), ConversasFragment.class)
                        .add(getResources().getString(R.string.fragment_contatos), ContatosFragment.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

    }
}
