package com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.creadormovimiento;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreadorMovimiento {

    public Movimiento crearMovimiento(int cursorClave,
                                      String cursorFecha,
                                      int cursorTipo,
                                      int cursorFormato,
                                      String cursorConcepto,
                                      float cursorFloat){
        Movimiento movimiento;

        int clave = cursorClave;
        String stringFecha = cursorFecha;
        DateTimeFormatter dateTimeFormatter = null;
        String formato;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            formato = "yyyy-MM-dd HH:mm";
            //if (stringFecha.charAt(4)!='-'){
                //stringFecha = stringFecha.replace("PM","p. m.");
                //stringFecha = stringFecha.replace("AM","a. m.");
            //    formato = "yyyyMMdd hh:mm:ss a";
            //}

            dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
        }
        LocalDateTime fecha = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try{
                fecha = LocalDateTime.parse(stringFecha,dateTimeFormatter);
            } catch(DateTimeParseException e1){
                try{
                    formato = "yyyyMMdd hh:mm:ss a";
                    dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
                    fecha = LocalDateTime.parse(stringFecha,dateTimeFormatter);
                }catch(DateTimeParseException e2){
                    stringFecha = stringFecha.replace("PM","p. m.");
                    stringFecha = stringFecha.replace("AM","a. m.");
                    formato = "yyyyMMdd hh:mm:ss a";
                    dateTimeFormatter = DateTimeFormatter.ofPattern(formato);
                    fecha = LocalDateTime.parse(stringFecha,dateTimeFormatter);
                }
            }

        }
        int intTipo = cursorTipo;
        TipoMovimiento tipoMovimiento = null;
        if (intTipo==0){
            tipoMovimiento = TipoMovimiento.GASTO;
        } else if (intTipo==1){
            tipoMovimiento = TipoMovimiento.GANANCIA;
        }
        int intFormato = cursorFormato;
        FormatoMovimiento formatoMovimiento = null;
        if (intFormato==0){
            formatoMovimiento = FormatoMovimiento.METALICO;
        } else if (intFormato==1){
            formatoMovimiento = FormatoMovimiento.DIGITAL;
        }
        String concepto = cursorConcepto;
        BigDecimal cantidad = BigDecimal.valueOf(cursorFloat);

        movimiento = new Movimiento(clave,fecha, tipoMovimiento, formatoMovimiento,concepto,cantidad);

        return movimiento;
    }

}
