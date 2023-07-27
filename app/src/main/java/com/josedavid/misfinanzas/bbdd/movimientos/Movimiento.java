package com.josedavid.misfinanzas.bbdd.movimientos;

import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.otros.version.AppVersion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Movimiento implements Serializable {
    private int clave;
    private LocalDateTime fecha;
    private TipoMovimiento tipoMovimiento;
    private FormatoMovimiento formatoMovimiento;
    private String concepto;
    private BigDecimal cantidad;
    private boolean seDebeDevolver;
    private boolean conFechaDevuelta;
    private LocalDateTime fechaDevuelta;
    private AppVersion appVersion;

    public Movimiento(LocalDateTime fecha,
                      TipoMovimiento tipoMovimiento,
                      FormatoMovimiento formatoMovimiento,
                      String concepto,
                      BigDecimal cantidad,
                      boolean seDebeDevolver,
                      boolean conFechaDevuelta,
                      LocalDateTime fechaDevuelta,
                      AppVersion appVersion) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.formatoMovimiento = formatoMovimiento;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.seDebeDevolver = seDebeDevolver;
        this.conFechaDevuelta = conFechaDevuelta;
        this.fechaDevuelta = fechaDevuelta;
        this.appVersion = appVersion;
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

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public FormatoMovimiento getFormatoMovimiento() {
        return formatoMovimiento;
    }

    public void setFormatoMovimiento(FormatoMovimiento formatoMovimiento) {
        this.formatoMovimiento = formatoMovimiento;
    }

    public boolean isSeDebeDevolver() {
        return seDebeDevolver;
    }

    public void setSeDebeDevolver(boolean seDebeDevolver) {
        this.seDebeDevolver = seDebeDevolver;
    }

    public boolean isConFechaDevuelta() {
        return conFechaDevuelta;
    }

    public void setConFechaDevuelta(boolean conFechaDevuelta) {
        this.conFechaDevuelta = conFechaDevuelta;
    }

    public LocalDateTime getFechaDevuelta() {
        return fechaDevuelta;
    }

    public void setFechaDevuelta(LocalDateTime fechaDevuelta) {
        this.fechaDevuelta = fechaDevuelta;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }
}
