package com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.insertarmovimiento;

import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;

public class InsertarMovimiento {

    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;

    public void insertarMovimiento(String nombreUsuario, Movimiento movimiento){
        sqLiteDatabase.execSQL(
                queriesStrings.getStatementInsertarMovimientoEnTablaDeUsuario(
                        nombreUsuario,movimiento));

    }

    public InsertarMovimiento(SQLiteDatabase sqLiteDatabase, QueriesStrings queriesStrings){

        this.sqLiteDatabase = sqLiteDatabase;
        this.queriesStrings = queriesStrings;

    }

}
