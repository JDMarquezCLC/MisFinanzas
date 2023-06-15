package com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.contardecimales;

public class ContadorDecimales {

    public int contarDecimalesNumeroEnString(String numero){
        int cantidadDecimales;

        if (numero.contains(".")){
            String decimales = numero.substring(numero.lastIndexOf("."));

            cantidadDecimales = decimales.length()-1;
        } else{
            cantidadDecimales = 0;
        }



        return cantidadDecimales;
    }
}
