package com.josedavid.misfinanzas.bbdd.gestor.modificadorusuario;

import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;

import java.math.BigDecimal;

public class ModificadorUsuario {
    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;

    public void actualizarTotalUsuario(int clave, BigDecimal total){
        String[] argumentos = new String[]{String.valueOf(clave)};
        String statement = queriesStrings.getSetDineroTotalUsuario(String.valueOf(total));
        sqLiteDatabase.execSQL(statement,argumentos);
    }

    public void actualizarUsuarioHaHechoSuPrimerMovimiento(String nombreUsuario){
        String[] argumentos = new String[]{nombreUsuario};
        sqLiteDatabase.execSQL(
                queriesStrings.getStatementUpdateAtributoPrimerCuentaUsuario(),argumentos);
    }

    public void actualizarNombreUsuario(String nuevoNombre, Usuario usuario){
        String[] argumentos = new String[]{String.valueOf(usuario.getClave())};
        sqLiteDatabase.execSQL(
                queriesStrings.getStatementUpdateNombreCuentaUsuario(nuevoNombre),argumentos);
        sqLiteDatabase.execSQL(
                queriesStrings.getStatementActualizarNombreTablaMovimientosUsuario(nuevoNombre,usuario.getNombre())
        );
    }

    public void actualizarPassUsuario(String nuevoPass, Usuario usuario){
        String[] argumentos = new String[]{String.valueOf(usuario.getClave())};
        sqLiteDatabase.execSQL(
                queriesStrings.getStatementUpdatePassCuentaUsuario(nuevoPass),argumentos);
    }

    public ModificadorUsuario(SQLiteDatabase sqLiteDatabase, QueriesStrings queriesStrings) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.queriesStrings = queriesStrings;
    }
}
