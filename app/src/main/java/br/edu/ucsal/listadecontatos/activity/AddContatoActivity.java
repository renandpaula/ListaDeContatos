package br.edu.ucsal.listadecontatos.activity;

import android.content.ContentValues;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import com.vicmikhailau.maskededittext.MaskedEditText;

import br.edu.ucsal.listadecontatos.R;
import br.edu.ucsal.listadecontatos.helper.ContatoDAO;
import br.edu.ucsal.listadecontatos.model.Contato;

public class AddContatoActivity extends AppCompatActivity {

    private TextInputEditText editContatoNome, editContatoEmail, editContatoEndereco, editContatoCidade;
    private MaskedEditText editContatoTelefone, editContatoCEP;
    private Spinner spinnerContatoUF;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contato);

        editContatoNome = findViewById(R.id.add_nome);
        editContatoTelefone =  findViewById(R.id.add_telefone);
        editContatoEmail =  findViewById(R.id.add_email);
        editContatoEndereco = findViewById(R.id.add_endereco);
        editContatoCidade = findViewById(R.id.add_cidade);
        editContatoCEP = findViewById(R.id.add_cep);
        spinnerContatoUF =  findViewById(R.id.add_uf_spinner);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_addcontato, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.contatoSalvar) {

            //Salva o contato no banco
            ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

            String nome = editContatoNome.getText().toString();
            String telefone = editContatoTelefone.getText().toString();
            String email = editContatoEmail.getText().toString();
            String endereco = editContatoEndereco.getText().toString();
            String cidade = editContatoCidade.getText().toString();
            String cep = editContatoCEP.getText().toString();
            String uf = spinnerContatoUF.getSelectedItem().toString();
            Contato contato = new Contato();
            contato.setNome(nome);
            contato.setTelefone(telefone);
            contato.setEmail(email);
            contato.setEndereco(endereco);
            contato.setCidade(cidade);
            contato.setCep(cep);
            contato.setUf(uf);
            contatoDAO.salvar(contato);
            Toast.makeText(AddContatoActivity.this, "Contato Salvo",
                                Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
