package com.josedavid.misfinanzas.bbdd.queries;

import com.josedavid.misfinanzas.bbdd.conversormovimiento.ConversorMovimiento;
import com.josedavid.misfinanzas.bbdd.conversormovimiento.MovimientoConvertido;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;

import java.math.BigDecimal;

public class QueriesStrings {

    // Instanciar este objeto para hacer consultas en vez de ponerlas directamente
    private String nombreTablaCuentasUsuarios;
    private String limpiarTablaCuentasUsuarios;
    private String seleccionarTodosLosUsuarios;
    private String seleccionarUsuarioPorClave;
    private String nombreTablasMovimientos;
    private String setDineroTotalUsuario;

    public QueriesStrings(){
        this.nombreTablaCuentasUsuarios = "CuentasUsuarios";
        this.nombreTablasMovimientos = "Movimientos";
        this.limpiarTablaCuentasUsuarios = "DELETE FROM " + nombreTablaCuentasUsuarios;
        this.seleccionarTodosLosUsuarios = "SELECT clave, nombre, password, primer, total FROM " + nombreTablaCuentasUsuarios;
        this.seleccionarUsuarioPorClave = "SELECT clave, nombre, password, primer, total FROM "+ nombreTablaCuentasUsuarios + " WHERE clave = ?";
    }

    public String getBorrarMovimientoUsuario(String nombreUsuario){
        String statement = "";

        statement = "DELETE FROM " + nombreTablasMovimientos + nombreUsuario + " WHERE clave = ?";

        return statement;
    }

    public String getSetDineroTotalUsuario(String total) {
        String statement = "UPDATE " + nombreTablaCuentasUsuarios
                + " SET total = " + total + " WHERE clave = ?";
        return statement;
    }

    public String getStatementUpdateAtributoPrimerCuentaUsuario(){
        String statement = "UPDATE " + nombreTablaCuentasUsuarios + " SET primer = 1 WHERE nombre=?";
        return statement;
    }

    public String getStatementUpdateNombreCuentaUsuario(String nuevonombre){
        String statement = "UPDATE " + nombreTablaCuentasUsuarios + " SET nombre='" + nuevonombre + "' WHERE clave=?";
        return statement;
    }

    public String getStatementUpdatePassCuentaUsuario(String nuevopass){
        String statement = "UPDATE " + nombreTablaCuentasUsuarios + " SET password='" + nuevopass + "' WHERE clave=?";
        return statement;
    }

    public String getStatementActualizarNombreTablaMovimientosUsuario(String nuevoNombre, String antiguoNombre){
        String statement = "ALTER TABLE " + nombreTablasMovimientos + antiguoNombre + " RENAME TO " + nombreTablasMovimientos + nuevoNombre;
        return statement;
    }

    public String getStatementInsertarMovimientoEnTablaDeUsuario(String nombreUsuario,
                                                                 Movimiento movimiento){

        ConversorMovimiento conversorMovimiento = new ConversorMovimiento();
        MovimientoConvertido movimientoConvertido;
        movimientoConvertido = conversorMovimiento.convertirMovimientonormalAMovimientoBBDD(
                movimiento);

        String statementInsertarDatos = "INSERT INTO Movimientos" + nombreUsuario + "(" +
                "fecha,tipo,formato,concepto,cantidad) VALUES (" +
                movimientoConvertido.getDatosParaInsertar() + ")";

        return statementInsertarDatos;
    }

    public String getStatementCrearTablaMovimientosUsuario(String nombreUsuario){
        String statementCrearTabla = "CREATE TABLE Movimientos" + nombreUsuario + "(" +
                "clave INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "fecha DATETIME, " +
                "tipo TINYINT(1), " +  // Tipo 0 es gasto, tipo 1 es ganancia
                "formato TINYINT(1), " + // Formato 0 es metálico, formato 1 es digital
                "concepto TEXT, " +
                "cantidad DECIMAL(16,2))";

        return statementCrearTabla;
    }

    public String getNombreTablaCuentasUsuarios() {
        return nombreTablaCuentasUsuarios;
    }

    public void setNombreTablaCuentasUsuarios(String nombreTablaCuentasUsuarios) {
        this.nombreTablaCuentasUsuarios = nombreTablaCuentasUsuarios;
    }

    public String getLimpiarTablaCuentasUsuarios() {
        return limpiarTablaCuentasUsuarios;
    }

    public void setLimpiarTablaCuentasUsuarios(String limpiarTablaCuentasUsuarios) {
        this.limpiarTablaCuentasUsuarios = limpiarTablaCuentasUsuarios;
    }

    public String getSeleccionarTodosLosUsuarios() {
        return seleccionarTodosLosUsuarios;
    }

    public void setSeleccionarTodosLosUsuarios(String seleccionarTodosLosUsuarios) {
        this.seleccionarTodosLosUsuarios = seleccionarTodosLosUsuarios;
    }

    public String getSeleccionarUsuarioPorClave() {
        return seleccionarUsuarioPorClave;
    }

    public void setSeleccionarUsuarioPorClave(String seleccionarUsuarioPorClave) {
        this.seleccionarUsuarioPorClave = seleccionarUsuarioPorClave;
    }

    public String getinsertarNuevoUsuario(String nombre, String password){
        String insertarNuevoUsuario = "INSERT INTO CuentasUsuarios(nombre,primer,password) " +
                "VALUES (" + "'" + nombre + "'," + "0," +  "'" + password + "'" + ")";
        return insertarNuevoUsuario;
    }

    public String getSelectAllFromTablaMovimientosUsuario(String nombreUsuario){
        String query = "SELECT clave, fecha, tipo, formato, concepto, cantidad FROM ";
        query = query + nombreTablasMovimientos;
        query = query + nombreUsuario;
        return query;

    }

}
