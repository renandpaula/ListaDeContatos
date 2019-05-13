package br.edu.ucsal.listadecontatos.activity;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
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

public class EditarContatoActivity extends AppCompatActivity {

    private TextInputEditText editContatoNome, editContatoEmail, editContatoEndereco, editContatoCidade;
    private MaskedEditText editContatoTelefone, editContatoCEP;
    private Spinner spinnerContatoUF;
    private Contato contatoAtual;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_contato);

        editContatoNome = findViewById(R.id.edit_nome);
        editContatoTelefone =  findViewById(R.id.edit_telefone);
        editContatoEmail =  findViewById(R.id.edit_email);
        editContatoEndereco = findViewById(R.id.edit_endereco);
        editContatoCidade = findViewById(R.id.edit_cidade);
        editContatoCEP = findViewById(R.id.edit_cep);
        spinnerContatoUF =  findViewById(R.id.edit_uf_spinner);

        //recuperar contato
        contatoAtual = (Contato) getIntent().getSerializableExtra("contatoSelecionado");



        //configurar as caixas de texto
        if (contatoAtual != null ){
            editContatoNome.setText(contatoAtual.getNome());
            editContatoTelefone.setText(contatoAtual.getTelefone());
            editContatoEmail.setText(contatoAtual.getEmail());
            editContatoEndereco.setText(contatoAtual.getEndereco());
            editContatoCidade.setText(contatoAtual.getCidade());
            editContatoCEP.setText(contatoAtual.getCep());
            spinnerContatoUF.setSelection(contatoAtual.getPosition());
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editcontato, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.contatoEditar :

                String nome = editContatoNome.getText().toString();
                String telefone = editContatoTelefone.getText().toString();
                String email = editContatoEmail.getText().toString();
                String endereco = editContatoEndereco.getText().toString();
                String cidade = editContatoCidade.getText().toString();
                String cep = editContatoCEP.getText().toString();
                String uf = spinnerContatoUF.getSelectedItem().toString();
                int position = spinnerContatoUF.getSelectedItemPosition();

                //verifica se ao menos o nome e o telefone estao preenchidos
                if (!nome.isEmpty()) {
                    if(!telefone.isEmpty()){
                        //Salva o contato no banco
                        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                        Contato contato = new Contato();
                        contato.setNome(nome);
                        contato.setId(contatoAtual.getId());
                        contato.setTelefone(telefone);
                        contato.setEmail(email);
                        contato.setEndereco(endereco);
                        contato.setCidade(cidade);
                        contato.setCep(cep);
                        contato.setUf(uf);
                        contato.setPosition(position);

                        if (contatoDAO.atualizar(contato)) {
                            finish();
                            Toast.makeText(EditarContatoActivity.this, "Contato salvo com sucesso",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarContatoActivity.this, "Erro ao salvar contato",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(EditarContatoActivity.this, "Preencha ao menos o nome e telefone do contato.",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditarContatoActivity.this, "Preencha ao menos o nome e telefone do contato.",
                            Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.contatoExcluir :

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditarContatoActivity.this);

                //configura o titulo da mensagem
                dialog.setTitle("Confirmar exclusão");
                dialog.setMessage("Deseja excluir o contato: " + contatoAtual.getNome() + " ?");

                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContatoDAO contatoDAO = new ContatoDAO(getApplicationContext());

                        if (contatoDAO.deletar(contatoAtual)){

                            finish();
                            Toast.makeText(EditarContatoActivity.this, "Contato deletado com sucesso!",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(EditarContatoActivity.this, "Erro ao excluir contato!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                dialog.setNegativeButton("Não", null);

                //exibir dialog
                dialog.create();
                dialog.show();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
