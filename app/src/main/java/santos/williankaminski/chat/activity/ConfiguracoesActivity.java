package santos.williankaminski.chat.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import santos.williankaminski.chat.R;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class ConfiguracoesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        initComponents();
        confgToolbar();
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
