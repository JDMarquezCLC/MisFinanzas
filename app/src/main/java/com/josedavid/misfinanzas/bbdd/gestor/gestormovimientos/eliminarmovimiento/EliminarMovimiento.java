package com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.eliminarmovimiento;

import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;

public class EliminarMovimiento {

    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;

    public void eliminarMovimiento(String nombreUsuario, Movimiento movimiento){
        String[] argumentos = new String[]{String.valueOf(movimiento.getClave())};
        String statement = queriesStrings.getBorrarMovimientoUsuario(nombreUsuario);
        sqLiteDatabase.execSQL(statement,argumentos);

    }

    public EliminarMovimiento(SQLiteDatabase sqLiteDatabase, QueriesStrings queriesStrings) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.queriesStrings = queriesStrings;
    }
}
