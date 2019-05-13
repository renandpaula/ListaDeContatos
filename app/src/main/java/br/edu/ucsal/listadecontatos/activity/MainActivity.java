package br.edu.ucsal.listadecontatos.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

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
    private Contato contatoSelecionado;

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


                                //recupera a posição do contato
                                Contato contatoSelecionado = listaContatos.get( position );

                                //envia o contato para tela de editar contato
                                Intent intent = new Intent(MainActivity.this, EditarContatoActivity.class);
                                intent.putExtra("contatoSelecionado", contatoSelecionado);

                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //recupera o contato para deletar
                                contatoSelecionado =  listaContatos.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //configura o titulo da mensagem
                                dialog.setTitle("Confirmar exclusão");
                                dialog.setMessage("Deseja excluir o contato: " + contatoSelecionado.getNome() + " ?");

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                                        if (contatoDAO.deletar(contatoSelecionado)){

                                            carregarListaContatos();
                                            Toast.makeText(MainActivity.this, "Contato deletado com sucesso!",
                                                    Toast.LENGTH_SHORT).show();

                                        }else {
                                            Toast.makeText(MainActivity.this, "Erro ao excluir contato!",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                dialog.setNegativeButton("Não", null);

                                //exibir dialog
                                dialog.create();
                                dialog.show();

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


}
