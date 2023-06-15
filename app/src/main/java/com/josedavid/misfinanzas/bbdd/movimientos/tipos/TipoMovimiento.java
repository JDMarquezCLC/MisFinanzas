package com.josedavid.misfinanzas.bbdd.movimientos.tipos;

public enum TipoMovimiento {
    GASTO(0),
    GANANCIA(1);

    private int valor;

    TipoMovimiento(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }
}
