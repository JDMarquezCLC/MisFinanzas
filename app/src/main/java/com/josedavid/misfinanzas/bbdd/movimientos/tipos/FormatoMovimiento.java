package com.josedavid.misfinanzas.bbdd.movimientos.tipos;

public enum FormatoMovimiento {
    METALICO(0),
    DIGITAL(1);

    private int valor;

    FormatoMovimiento(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }
}
