package br.edu.ucsal.listadecontatos.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ucsal.listadecontatos.model.Contato;

public class ContatoDAO implements IContatoDAO {
    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public ContatoDAO(Context context) {

        DatabaseHelper db = new DatabaseHelper(context);
        escreve =  db.getReadableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Contato contato) {

        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNome());
        cv.put("telefone", contato.getTelefone());
        cv.put("email", contato.getEmail());
        cv.put("endereco", contato.getEndereco());
        cv.put("cidade", contato.getCidade());
        cv.put("cep", contato.getCep());
        cv.put("uf", contato.getUf());

        try {
            escreve.insert(DatabaseHelper.TABELA_CONTATOS, null, cv);
            Log.i("INFO", "Contato salvo com no Database" );
        } catch (Exception e){
            Log.e("INFO", "Erro ao salvar contato" + e.getMessage());
            return false;
        }


        return true;
    }

    @Override
    public boolean atualizar(Contato contato) {
        return false;
    }

    @Override
    public boolean deletar(Contato contato) {
        return false;
    }

    @Override
    public List<Contato> listar() {
        List<Contato> contatos = new ArrayList<>();

        String sql = "SELECT * FROM " + DatabaseHelper.TABELA_CONTATOS + " ;";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()) {

            Contato contato = new Contato();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeContato =  c.getString(c.getColumnIndex("nome"));

            contato.setId(id);
            contato.setNome(nomeContato);

            contatos.add(contato);
        }

        return contatos;
    }
}
