package com.josedavid.misfinanzas;

import static org.junit.Assert.assertEquals;

import android.text.Editable;
import android.widget.EditText;

import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.ConversorCantidad;

import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

public class OtrosTests {



    @Test
    public void comprobarConversionTextFieldABigDecimal(){
        BigDecimal bigDecimalEsperado = new BigDecimal("49.20");
        BigDecimal bigDecimalResultado;
        EditText editText = mock(EditText.class);
        Editable editableDelEditText = mock(Editable.class);
        Mockito.when(editableDelEditText.toString()).thenReturn("49,20");
        Mockito.when(editText.getText()).thenReturn(editableDelEditText);
        ConversorCantidad conversorCantidad = new ConversorCantidad();
        bigDecimalResultado = conversorCantidad.convertirTextFieldABigDecimal(editText);
        assertEquals(bigDecimalEsperado,bigDecimalResultado);
    }

}
