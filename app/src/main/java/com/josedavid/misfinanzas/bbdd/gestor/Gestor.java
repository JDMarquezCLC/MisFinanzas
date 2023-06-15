package com.josedavid.misfinanzas.bbdd.gestor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.josedavid.misfinanzas.bbdd.Helper;
import com.josedavid.misfinanzas.bbdd.gestor.creadorusuario.CreadorUsuario;
import com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.GestorMovimientos;
import com.josedavid.misfinanzas.bbdd.gestor.modificadorusuario.ModificadorUsuario;
import com.josedavid.misfinanzas.bbdd.gestor.obtencionusuarios.ObtencionUsuarios;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.bbdd.queries.QueriesStrings;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Gestor {
    private Context context;
    private Helper helper;
    private SQLiteDatabase sqLiteDatabase;
    private QueriesStrings queriesStrings;
    private ObtencionUsuarios obtencionUsuarios;
    private CreadorUsuario creadorUsuario;
    private GestorMovimientos gestorMovimientos;
    private ModificadorUsuario modificadorUsuario;

    public ArrayList<Movimiento> getListaMovimientosUsuario(String nombreUsuario){
        return gestorMovimientos.getListaMovimientosUsuario(nombreUsuario);

    }

    public void limpiarTablaUsuarios(){
        sqLiteDatabase.execSQL(
                queriesStrings.getLimpiarTablaCuentasUsuarios()
        );
    }

    public ArrayList<Usuario> obtenerUsuarios(){
        return obtencionUsuarios.obtenerTodosLosUsuarios();
    }

    public Usuario obtenerUsuarioPorClave(int clave){
        return obtencionUsuarios.obtenerUsuarioPorClave(clave);
    }

    public void insertarNuevoUsuario(String nombre, String password){
        creadorUsuario.crearUsuario(nombre, password);
    }

    public void actualizarUsuarioPrimerMovimientoHecho(String nombreUsuario){
        modificadorUsuario.actualizarUsuarioHaHechoSuPrimerMovimiento(nombreUsuario);
    }

    public void actualizarNombreUsuario(String nuevoNombre, Usuario usuario){
        modificadorUsuario.actualizarNombreUsuario(nuevoNombre,usuario);
    }

    public void actualizarPassUsuario(String nuevoPass, Usuario usuario){
        modificadorUsuario.actualizarPassUsuario(nuevoPass,usuario);
    }

    public Usuario insertarNuevaEntradaTablaUsuario(Usuario usuario, Movimiento movimiento){
        BigDecimal total;
        total = usuario.getDineroTotal();
        if (movimiento.getTipo()== TipoMovimiento.GANANCIA){
            total = total.add(movimiento.getCantidad());
        } else if (movimiento.getTipo()== TipoMovimiento.GASTO){
            total = total.subtract(movimiento.getCantidad());
        }
        gestorMovimientos.insertarMovimiento(usuario.getNombre(),movimiento);
        modificadorUsuario.actualizarTotalUsuario(usuario.getClave(), total);
        usuario = obtenerUsuarioPorClave(usuario.getClave());
        return usuario;
    }

    public void eliminarMovimientoUsuario(Usuario usuario, Movimiento movimiento){
        gestorMovimientos.eliminarMovimiento(usuario.getNombre(),movimiento);
    }

    public void actualizarDineroTotalUsuario(int clave, BigDecimal total){
        modificadorUsuario.actualizarTotalUsuario(clave,total);
    }

    public Gestor(Context context) {
        this.queriesStrings = new QueriesStrings();
        this.context = context;
        this.helper = new Helper(context,"DBMisFinanzas",null,6);
        this.sqLiteDatabase = helper.getWritableDatabase();
        this.obtencionUsuarios = new ObtencionUsuarios(sqLiteDatabase,queriesStrings);
        this.creadorUsuario = new CreadorUsuario(sqLiteDatabase,queriesStrings,helper);
        this.gestorMovimientos = new GestorMovimientos(queriesStrings,sqLiteDatabase);
        this.modificadorUsuario = new ModificadorUsuario(sqLiteDatabase,queriesStrings);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Helper getBbddHelper() {
        return helper;
    }

    public void setBbddHelper(Helper bbddHelper) {
        this.helper = bbddHelper;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

}
