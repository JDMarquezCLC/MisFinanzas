package com.josedavid.misfinanzas.actividades.pantallainformes.convertirbdaint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ConvertirBDAInt {
    public ArrayList<Integer> convertirListaBDAInt(ArrayList<BigDecimal> listaBD){
        ArrayList<Integer> listaRetorno = new ArrayList<>();

        for (int i = 0;i<listaBD.size();i++){
            BigDecimal numero = listaBD.get(i);
            Integer numeroConvertido = numero.setScale(0, RoundingMode.DOWN).intValueExact();
            listaRetorno.add(numeroConvertido);
        }

        return listaRetorno;
    }
}
