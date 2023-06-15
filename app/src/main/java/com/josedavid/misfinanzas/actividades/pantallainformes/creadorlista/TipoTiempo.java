package com.josedavid.misfinanzas.actividades.pantallainformes.creadorlista;

public enum TipoTiempo {
    DIAS(0),
    MESES(1),
    YEARS(2);

    private int valor;
    TipoTiempo(int i) {
        this.valor = i;
    }

    public int getValor() {
        return valor;
    }

    public static TipoTiempo getTipoTiempoByValue(int value){
        TipoTiempo tipoTiempoRetorno = null;
        for (int i = 0;i<TipoTiempo.values().length;i++){
            if (TipoTiempo.values()[i].getValor()==value){
                tipoTiempoRetorno = TipoTiempo.values()[i];
            }

        }
        return tipoTiempoRetorno;
    }
}
