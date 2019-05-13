package br.edu.ucsal.listadecontatos.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.edu.ucsal.listadecontatos.R;
import br.edu.ucsal.listadecontatos.adapter.ContatoAdapter;
import br.edu.ucsal.listadecontatos.helper.ContatoDAO;
import br.edu.ucsal.listadecontatos.helper.DatabaseHelper;
import br.edu.ucsal.listadecontatos.helper.RecyclerItemClickListener;
import br.edu.ucsal.listadecontatos.model.Contato;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContatoAdapter contatoAdapter;
    private List<Contato> listaContatos =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurando o Recycler
        recyclerView =  findViewById(R.id.main_recyclerView_contatos);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.i("clique", "onItemClick");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Log.i("clique", "onLongItemClick");
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )

        );

        FloatingActionButton fab = findViewById(R.id.main_fab_AddContato);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContatoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaContatos(){

        //Listas contatos
        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());
        listaContatos = contatoDAO.listar();


        /*
            Exibe a lista de contatos no RecyclerView
         */

        //Configuracao do Adapter
        contatoAdapter = new ContatoAdapter( listaContatos );

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter( contatoAdapter );

    }

    @Override
    protected void onStart() {
        carregarListaContatos();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
