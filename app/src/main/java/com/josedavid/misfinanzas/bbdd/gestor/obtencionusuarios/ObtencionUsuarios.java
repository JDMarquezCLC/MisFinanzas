package com.josedavid.misfinanzas.bbdd.gestor.obtencionusuarios;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ObtencionUsuarios {

    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;


    public ArrayList<Usuario> obtenerTodosLosUsuarios(){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(queriesStrings.getSeleccionarTodosLosUsuarios(),null);

        while (cursor.moveToNext()){
            Usuario usuario = new Usuario(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getFloat(4));
            listaUsuarios.add(usuario);
        }

        return listaUsuarios;
    }

    public Usuario obtenerUsuarioPorClave(int clave){
        Usuario usuario = null;
        String[] argumentos = new String[]{
                String.valueOf(clave)
        };

        Cursor cursor = sqLiteDatabase.rawQuery(
                queriesStrings.getSeleccionarUsuarioPorClave(),
                argumentos);

        while (cursor.moveToNext()){
            usuario = new Usuario(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3),cursor.getFloat(4));
        }

        return usuario;
    }


    public ObtencionUsuarios(SQLiteDatabase sqLiteDatabase, QueriesStrings queriesStrings) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.queriesStrings = queriesStrings;
    }
}
