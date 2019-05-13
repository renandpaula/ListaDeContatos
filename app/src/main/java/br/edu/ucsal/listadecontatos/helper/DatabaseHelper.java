package br.edu.ucsal.listadecontatos.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_CONTATOS;";
    public static String TABELA_CONTATOS = "contatos";


    public DatabaseHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_CONTATOS
                   + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + " nome VARCHAR(30) NOT NULL, "
                     + " telefone VARCHAR(20) NOT NULL, "
                      + " email VARCHAR(50), "
                        + " endereco VARCHAR(50), "
                         + " cidade VARCHAR(20), "
                          + " uf VARCHAR(2), "
                            + " cep VARCHAR(9), "
                             + "position INTEGER ); ";

        try {

            db.execSQL( sql );
            Log.i("INFO DB", "Sucesso ao criar a tabela" );

        } catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage() );
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
