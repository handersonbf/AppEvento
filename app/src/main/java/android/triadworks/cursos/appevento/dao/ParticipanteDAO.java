package android.triadworks.cursos.appevento.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.triadworks.cursos.appevento.modelo.Participante;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by handersonbf on 19/08/16.
 */
public class ParticipanteDAO extends SQLiteOpenHelper {
    private static final String DATABASE = "appEventos";
    private static final String TABELA = "Participantes";
    private static final int VERSAO = 3;
    private static final String COL_ID = "id";
    private static final String COL_NOME = "nome";
    private static final String COL_EMAIL = "email";
    private static final String COL_TELEFONE = "telefone";
    private static final String COL_ENDERECO = "endereco";
    private static final String COL_CAMINHO_FOTO = "caminhoFoto";

    public ParticipanteDAO(Context context) {
        super(context, DATABASE, null, VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + " ("
                + COL_ID + " INTEGER PRIMARY KEY, "
                + COL_NOME + " TEXT UNIQUE NOT NULL, "
                + COL_EMAIL + " TEXT, "
                + COL_TELEFONE + " TEXT,"
                + COL_ENDERECO + " TEXT,"
                + COL_CAMINHO_FOTO + " TEXT"
                + ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE " + TABELA + " ADD COLUMN " + COL_ENDERECO + " TEXT;";
                db.execSQL(sql);
            case 2:
                sql = "ALTER TABLE " + TABELA + " ADD COLUMN " + COL_CAMINHO_FOTO + " TEXT;";
                db.execSQL(sql);
        }
    }

    public void inserir(Participante participante){
        ContentValues values = new ContentValues();
        values.put(COL_NOME, participante.getNome());
        values.put(COL_EMAIL, participante.getEmail());
        values.put(COL_TELEFONE, participante.getTelefone());
        values.put(COL_ENDERECO, participante.getEndereco());
        values.put(COL_CAMINHO_FOTO, participante.getCaminhoFoto());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Participante> getLista(){
        List<Participante> lista = new ArrayList<>();

        String sql = "SELECT * FROM " + TABELA + ";";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        Participante participante = null;

        while(c.moveToNext()){
            participante = new Participante();

            participante.setId(c.getLong(c.getColumnIndex(COL_ID)));
            participante.setNome(c.getString(c.getColumnIndex(COL_NOME)));
            participante.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
            participante.setTelefone(c.getString(c.getColumnIndex(COL_TELEFONE)));
            participante.setEndereco(c.getString(c.getColumnIndex(COL_ENDERECO)));
            participante.setCaminhoFoto(c.getString(c.getColumnIndex(COL_CAMINHO_FOTO)));

            lista.add(participante);
        }

        return lista;
    }

    public void deletar(Participante participante) {
        String[] argumentos = {participante.getId().toString()};
        getWritableDatabase().delete(TABELA, COL_ID + "=?", argumentos);
    }

    public void alterar(Participante participante){
        ContentValues values = new ContentValues();
        values.put(COL_NOME, participante.getNome());
        values.put(COL_EMAIL, participante.getEmail());
        values.put(COL_TELEFONE, participante.getTelefone());
        values.put(COL_ENDERECO, participante.getEndereco());
        values.put(COL_CAMINHO_FOTO, participante.getCaminhoFoto());

        String[] argumentos = {participante.getId().toString()};
        getWritableDatabase().update(TABELA, values, COL_ID + "=?", argumentos);
    }
}
