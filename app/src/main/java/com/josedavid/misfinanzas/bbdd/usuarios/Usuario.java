package com.josedavid.misfinanzas.bbdd.usuarios;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Usuario implements Serializable{
    private int clave;
    private String nombre;
    private String password;
    private ArrayList<Movimiento> listaMovimientos;
    private boolean primerMovimiento;
    private BigDecimal dineroTotal;


    public Usuario(int clave,
                   String nombre,
                   String password,
                   ArrayList<Movimiento> listaMovimientos,
                   boolean primerMovimiento,
                   BigDecimal dineroTotal) {
        this.clave = clave;
        this.nombre = nombre;
        this.password = password;
        this.listaMovimientos = listaMovimientos;
        this.primerMovimiento = primerMovimiento;
        this.dineroTotal = dineroTotal;
    }

    public Usuario(int clave,
                   String nombre,
                   String password,
                   ArrayList<Movimiento> listaMovimientos,
                   boolean primerMovimiento) {
        this.clave = clave;
        this.nombre = nombre;
        this.password = password;
        this.listaMovimientos = listaMovimientos;
        this.primerMovimiento = primerMovimiento;
    }

    public Usuario(int clave, String nombre, String password) {
        this.clave = clave;
        this.nombre = nombre;
        this.password = password;
        this.listaMovimientos = new ArrayList<>();
    }

    public Usuario(int clave, String nombre, String password, int primer) {
        this.clave = clave;
        this.nombre = nombre;
        this.password = password;
        this.listaMovimientos = new ArrayList<>();
        this.primerMovimiento = primer != 0;
    }

    public Usuario(int clave, String nombre, String password, int primer, float total) {
        this.clave = clave;
        this.nombre = nombre;
        this.password = password;
        this.listaMovimientos = new ArrayList<>();
        this.primerMovimiento = primer != 0;
        this.dineroTotal = new BigDecimal(total);
    }

    public boolean isPrimerMovimiento() {
        return primerMovimiento;
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    public ArrayList<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(ArrayList<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getDineroTotal() {
        return dineroTotal;
    }

    public void setDineroTotal(BigDecimal dineroTotal) {
        this.dineroTotal = dineroTotal;
    }
}
