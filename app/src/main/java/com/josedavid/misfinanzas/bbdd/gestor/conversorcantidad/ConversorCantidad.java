package com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad;

import android.text.Editable;
import android.widget.EditText;

import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.contardecimales.ContadorDecimales;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversorCantidad {

    private ContadorDecimales contadorDecimales;

    public BigDecimal convertirTextFieldABigDecimal(EditText editText){

        BigDecimal devuelta;

        Editable editable = editText.getText();
        String string = String.valueOf(editable);
        string = string.replaceAll(",",".");
        //Double nDouble = Double.parseDouble(string);
        //int cantidadDecimales = contadorDecimales.contarDecimalesNumeroEnString(string);
        devuelta = new BigDecimal(string).setScale(2, RoundingMode.HALF_EVEN);

        return devuelta;

    }

    public ConversorCantidad(){
        contadorDecimales = new ContadorDecimales();
    }

}
