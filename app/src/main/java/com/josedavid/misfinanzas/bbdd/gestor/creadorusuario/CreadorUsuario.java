package com.josedavid.misfinanzas.bbdd.gestor.creadorusuario;

import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.Helper;
import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;

public class CreadorUsuario {

    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;
    private Helper helper;

    public void crearUsuario(String nombre, String password){
        insertarNuevoUsuario(nombre, password);
        crearTablaUsuarioNuevo(nombre);
    }

    private void insertarNuevoUsuario(String nombre, String password){
        sqLiteDatabase.execSQL(queriesStrings.getinsertarNuevoUsuario(nombre, password));
    }

    private void crearTablaUsuarioNuevo(String nombreUsuario){
        sqLiteDatabase.execSQL(queriesStrings.getStatementCrearTablaMovimientosUsuario(nombreUsuario));

    }

    public CreadorUsuario(SQLiteDatabase sqLiteDatabase, QueriesStrings queriesStrings, Helper helper) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.queriesStrings = queriesStrings;
        this.helper = helper;
    }
}
