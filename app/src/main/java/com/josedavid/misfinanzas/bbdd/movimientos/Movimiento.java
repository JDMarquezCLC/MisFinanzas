package com.josedavid.misfinanzas.bbdd.movimientos;

import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movimiento implements Serializable {
    private int clave;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private FormatoMovimiento formatoMovimiento;
    private String concepto;
    private BigDecimal cantidad;

    public Movimiento(int clave,
                      LocalDateTime fecha,
                      TipoMovimiento tipoMovimiento,
                      FormatoMovimiento formatoMovimiento,
                      String concepto,
                      BigDecimal cantidad) {
        this.clave = clave;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.formatoMovimiento = formatoMovimiento;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public Movimiento(LocalDateTime fecha,
                      TipoMovimiento tipoMovimiento,
                      FormatoMovimiento formatoMovimiento,
                      String concepto,
                      BigDecimal cantidad) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.formatoMovimiento = formatoMovimiento;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public TipoMovimiento getTipo() {
        return tipoMovimiento;
    }

    public void setTipo(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public FormatoMovimiento getFormato() {
        return formatoMovimiento;
    }

    public void setFormato(FormatoMovimiento formatoMovimiento) {
        this.formatoMovimiento = formatoMovimiento;
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
}
