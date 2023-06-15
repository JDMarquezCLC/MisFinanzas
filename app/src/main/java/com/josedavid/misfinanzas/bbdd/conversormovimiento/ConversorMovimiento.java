package com.josedavid.misfinanzas.bbdd.conversormovimiento;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public class ConversorMovimiento {

    public MovimientoConvertido convertirMovimientonormalAMovimientoBBDD(
            Movimiento movimientoOriginal){

        MovimientoConvertido movimientoConvertido;

        int clave = movimientoOriginal.getClave();

        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMdd hh:mm:ss a");
        }

        String fecha = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fecha = movimientoOriginal.getFecha().format(formatter);
        }

        fecha = fecha.replace("a. m.","AM");
        fecha = fecha.replace("p. m.","PM");


        int tipo = movimientoOriginal.getTipo().getValor();
        int formato = movimientoOriginal.getFormato().getValor();
        String concepto = movimientoOriginal.getConcepto();
        BigDecimal cantidad = movimientoOriginal.getCantidad();



        movimientoConvertido = new MovimientoConvertido(clave,fecha,tipo,formato,concepto,cantidad);

        return movimientoConvertido;

    }

}
