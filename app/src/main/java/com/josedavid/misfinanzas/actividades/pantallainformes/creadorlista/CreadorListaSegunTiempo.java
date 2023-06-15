package com.josedavid.misfinanzas.actividades.pantallainformes.creadorlista;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CreadorListaSegunTiempo {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Movimiento> devolverMovimientosSegunTiempo(
            ArrayList<Movimiento> listaMovimientosCompleta,
            TipoTiempo tipoTiempo,
            int cantidad,
            LocalDateTime fechaParaBuscar){
        ArrayList<Movimiento> listaRetorno = new ArrayList<>();

        for (int i = 0;i<listaMovimientosCompleta.size();i++){
            Movimiento movimiento = listaMovimientosCompleta.get(i);
            LocalDateTime fecha = movimiento.getFecha();
            switch (tipoTiempo){
                case DIAS:
                    if(
                            fecha.getDayOfMonth()==fechaParaBuscar.getDayOfMonth() &&
                    fecha.getMonthValue() == fechaParaBuscar.getMonthValue() &&
                    fecha.getYear() == fechaParaBuscar.getYear()){
                        listaRetorno.add(movimiento);
                    }
                    break;
                case MESES:
                    if(
                            fecha.isAfter(
                                    fechaParaBuscar.minusMonths(cantidad))
                                    && fecha.isBefore(fechaParaBuscar.plusMonths(1))){
                        listaRetorno.add(movimiento);
                    }
                    break;
                case YEARS:
                    if(
                            fecha.isAfter(
                                    fechaParaBuscar.minusYears(cantidad))
                                    && fecha.isBefore(fechaParaBuscar.plusYears(1))){
                        listaRetorno.add(movimiento);
                    }
                    break;
            }
        }

        return listaRetorno;
    }

}
