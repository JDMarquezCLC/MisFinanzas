package com.josedavid.misfinanzas.bbdd.conversormovimiento;

import java.math.BigDecimal;

public class MovimientoConvertido {

    private int clave;
    private String fecha;
    private int tipo;
    private int formato;
    private String concepto;
    private BigDecimal cantidad;

    public MovimientoConvertido(int clave, String fecha, int tipo, int formato, String concepto, BigDecimal cantidad) {
        this.clave = clave;
        this.fecha = fecha;
        this.tipo = tipo;
        this.formato = formato;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getFormato() {
        return formato;
    }

    public void setFormato(int formato) {
        this.formato = formato;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getDatosParaInsertar(){
        String devuelta = "'" + fecha + "'," + tipo + "," + formato + ",'" + concepto + "'," + cantidad;
        return devuelta;
    }
}
