package com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.creadormovimiento.CreadorMovimiento;
import com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.eliminarmovimiento.EliminarMovimiento;
import com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.insertarmovimiento.InsertarMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;

import java.util.ArrayList;

public class GestorMovimientos {

    private QueriesStrings queriesStrings;
    private SQLiteDatabase sqLiteDatabase;
    private CreadorMovimiento creadorMovimiento;
    private InsertarMovimiento insertarMovimiento;
    private EliminarMovimiento eliminarMovimiento;

    public ArrayList<Movimiento> getListaMovimientosUsuario(String nombreUsuario){

        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(
                queriesStrings.getSelectAllFromTablaMovimientosUsuario(
                        nombreUsuario),
                null);

        while(cursor.moveToNext()){
            Movimiento movimiento = creadorMovimiento.crearMovimientoDesdeBBDD(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getFloat(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getString(8)

            );

            listaMovimientos.add(movimiento);

        }

        return listaMovimientos;

    }

    public void insertarMovimiento(String nombreUsuario, Movimiento movimiento){
        insertarMovimiento.insertarMovimiento(nombreUsuario,movimiento);
    }

    public void eliminarMovimiento(String nombreUsuario, Movimiento movimiento){
        eliminarMovimiento.eliminarMovimiento(nombreUsuario,movimiento);
    }

    public GestorMovimientos(QueriesStrings queriesStrings, SQLiteDatabase sqLiteDatabase) {
        this.queriesStrings = queriesStrings;
        this.sqLiteDatabase = sqLiteDatabase;
        this.creadorMovimiento = new CreadorMovimiento();
        this.insertarMovimiento = new InsertarMovimiento(sqLiteDatabase, queriesStrings);
        this.eliminarMovimiento = new EliminarMovimiento(sqLiteDatabase,queriesStrings);
    }
}
