package com.josedavid.misfinanzas.actividades.utilidades.informes.media;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class EstablecedorMediasYTotales {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<ArrayList<BigDecimal>> establecerMedia(
            ArrayList<Movimiento> listaMovimientos,
            LocalDateTime fechaSeleccionada){

        ArrayList<BigDecimal> listaMedias = new ArrayList<>();

        ArrayList<BigDecimal> listaTotales = new ArrayList<>();

        ArrayList<BigDecimal> listaTotalMovimientos = new ArrayList<>();

        ArrayList<ArrayList<BigDecimal>> listaRetorno = new ArrayList<>();


        ArrayList<BigDecimal> gananciasDia = new ArrayList<>();
        ArrayList<BigDecimal> gananciasMes = new ArrayList<>();
        ArrayList<BigDecimal> ganancias6Mes = new ArrayList<>();
        ArrayList<BigDecimal> gananciasYear = new ArrayList<>();

        ArrayList<BigDecimal> gastosDia = new ArrayList<>();
        ArrayList<BigDecimal> gastosMes = new ArrayList<>();
        ArrayList<BigDecimal> gastos6Mes = new ArrayList<>();
        ArrayList<BigDecimal> gastosYear = new ArrayList<>();

        for (int i = 0;i<listaMovimientos.size();i++){
            Movimiento movimientoActual = listaMovimientos.get(i);
            LocalDate fechaMovimiento = movimientoActual.getFecha().toLocalDate();
            LocalDate fechaActual = null;
            fechaActual = fechaSeleccionada.toLocalDate();
            switch(movimientoActual.getTipo()){
                case GASTO:
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusDays(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gastosDia.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusMonths(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gastosMes.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusMonths(
                                            6)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gastos6Mes.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusYears(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gastosYear.add(movimientoActual.getCantidad());

                    }
                    break;
                case GANANCIA:
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusDays(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gananciasDia.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusMonths(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gananciasMes.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusMonths(
                                            6)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        ganancias6Mes.add(movimientoActual.getCantidad());

                    }
                    if (
                            fechaMovimiento.isAfter(
                                    fechaActual.minusYears(
                                            1)) && fechaMovimiento.isBefore(
                                    fechaActual.plusDays(
                                            1))){
                        gananciasYear.add(movimientoActual.getCantidad());

                    }

                    break;
            }
        }
        BigDecimal totalGananciasDia = new BigDecimal(0);
        BigDecimal totalGananciasMes = new BigDecimal(0);
        BigDecimal totalGanancias6Mes = new BigDecimal(0);
        BigDecimal totalGananciasYear = new BigDecimal(0);
        BigDecimal totalGastosDia = new BigDecimal(0);
        BigDecimal totalGastosMes = new BigDecimal(0);
        BigDecimal totalGastos6Mes = new BigDecimal(0);
        BigDecimal totalGastosYear = new BigDecimal(0);

        for (int i=0;i<gananciasMes.size();i++){
            totalGananciasMes = totalGananciasMes.add(gananciasMes.get(i));
        }
        for (int i=0;i<ganancias6Mes.size();i++){
            totalGanancias6Mes = totalGanancias6Mes.add(ganancias6Mes.get(i));
        }
        for (int i=0;i<gananciasYear.size();i++){
            totalGananciasYear = totalGananciasYear.add(gananciasYear.get(i));
        }
        for (int i=0;i<gastosMes.size();i++){
            totalGastosMes = totalGastosMes.add(gastosMes.get(i));
        }
        for (int i=0;i<gastos6Mes.size();i++){
            totalGastos6Mes = totalGastos6Mes.add(gastos6Mes.get(i));
        }
        for (int i=0;i<gastosYear.size();i++){
            totalGastosYear = totalGastosYear.add(gastosYear.get(i));
        }

        for (int i=0;i<gananciasDia.size();i++){
            totalGananciasDia = totalGananciasDia.add(gananciasDia.get(i));
        }
        for (int i=0;i<gastosDia.size();i++){
            totalGastosDia = totalGastosDia.add(gastosDia.get(i));
        }

        BigDecimal mediaGananciaMes = BigDecimal.ZERO;
        BigDecimal mediaGanancia6Mes = BigDecimal.ZERO;
        BigDecimal mediaGananciaYear = BigDecimal.ZERO;
        BigDecimal mediaGastoMes = BigDecimal.ZERO;
        BigDecimal mediaGasto6Mes = BigDecimal.ZERO;
        BigDecimal mediaGastoYear = BigDecimal.ZERO;

        BigDecimal mediaGananciaDia = BigDecimal.ZERO;
        BigDecimal mediaGastoDia = BigDecimal.ZERO;

        if (totalGananciasDia.compareTo(BigDecimal.ZERO)>0){
            mediaGananciaDia = totalGananciasDia.divide(new BigDecimal(gananciasDia.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGananciasMes.compareTo(BigDecimal.ZERO)>0){
            mediaGananciaMes = totalGananciasMes.divide(new BigDecimal(gananciasMes.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGanancias6Mes.compareTo(BigDecimal.ZERO)>0){
            mediaGanancia6Mes = totalGanancias6Mes.divide(new BigDecimal(ganancias6Mes.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGananciasYear.compareTo(BigDecimal.ZERO)>0){
            mediaGananciaYear = totalGananciasYear.divide(new BigDecimal(gananciasYear.size()),2, RoundingMode.HALF_EVEN);
        }


        if (totalGastosDia.compareTo(BigDecimal.ZERO)>0){
            mediaGastoDia = totalGastosDia.divide(new BigDecimal(gastosDia.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGastosMes.compareTo(BigDecimal.ZERO)>0){
            mediaGastoMes = totalGastosMes.divide(new BigDecimal(gastosMes.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGastos6Mes.compareTo(BigDecimal.ZERO)>0){
            mediaGasto6Mes = totalGastos6Mes.divide(new BigDecimal(gastos6Mes.size()),2, RoundingMode.HALF_EVEN);
        }
        if (totalGastosYear.compareTo(BigDecimal.ZERO)>0){
            mediaGastoYear = totalGastosYear.divide(new BigDecimal(gastosYear.size()),2, RoundingMode.HALF_EVEN);
        }

        listaMedias.add(mediaGananciaMes);
        listaMedias.add(mediaGanancia6Mes);
        listaMedias.add(mediaGananciaYear);
        listaMedias.add(mediaGastoMes);
        listaMedias.add(mediaGasto6Mes);
        listaMedias.add(mediaGastoYear);

        listaMedias.add(mediaGananciaDia);
        listaMedias.add(mediaGastoDia);

        listaTotales.add(totalGananciasDia);
        listaTotales.add(totalGananciasMes);
        listaTotales.add(totalGanancias6Mes);
        listaTotales.add(totalGananciasYear);
        listaTotales.add(totalGastosDia);
        listaTotales.add(totalGastosMes);
        listaTotales.add(totalGastos6Mes);
        listaTotales.add(totalGastosYear);

        listaTotalMovimientos.add(new BigDecimal(gananciasDia.size()));
        listaTotalMovimientos.add(new BigDecimal(gananciasMes.size()));
        listaTotalMovimientos.add(new BigDecimal(ganancias6Mes.size()));
        listaTotalMovimientos.add(new BigDecimal(gananciasYear.size()));
        listaTotalMovimientos.add(new BigDecimal(gastosDia.size()));
        listaTotalMovimientos.add(new BigDecimal(gastosMes.size()));
        listaTotalMovimientos.add(new BigDecimal(gastos6Mes.size()));
        listaTotalMovimientos.add(new BigDecimal(gastosYear.size()));

        listaRetorno.add(listaMedias);
        listaRetorno.add(listaTotales);
        listaRetorno.add(listaTotalMovimientos);

        return listaRetorno;

    }

}
