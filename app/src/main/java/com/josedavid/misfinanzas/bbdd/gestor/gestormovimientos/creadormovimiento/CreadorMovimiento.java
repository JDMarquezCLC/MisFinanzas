package com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.creadormovimiento;

import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.otros.adaptadores.adaptadorfecha.AdaptadorFecha;
import com.josedavid.misfinanzas.otros.version.AppVersion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreadorMovimiento {

    public Movimiento crearMovimientoDesdeBBDD(Integer cursorClave,
                                               String cursorFecha,
                                               int cursorTipo,
                                               int cursorFormato,
                                               String cursorConcepto,
                                               float cursorFloat,
                                               Integer cursorSeDebeDevolver,
                                               Integer cursorConFechaDevuelta,
                                               String cursorFechaDevuelta
                                      ){
        Movimiento movimiento;

        AdaptadorFecha adaptadorFecha = new AdaptadorFecha();
        LocalDateTime fecha = adaptadorFecha.convertirFecha(cursorFecha);

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

        boolean seDebeDevolver = false;
        boolean conFechaDevuelta = false;
        LocalDateTime fechaDevuelta = null;

        if(cursorSeDebeDevolver==1){
            seDebeDevolver = true;
        }
        if(cursorConFechaDevuelta==1){
            conFechaDevuelta= true;
        }
        if(cursorFechaDevuelta!=null){
            fechaDevuelta = adaptadorFecha.convertirFecha(cursorFechaDevuelta);
        }

        movimiento = new Movimiento(
                fecha,
                tipoMovimiento,
                formatoMovimiento,
                concepto,
                cantidad,
                seDebeDevolver,
                conFechaDevuelta,
                fechaDevuelta,
                new AppVersion());

        if(cursorClave!=null){
            movimiento.setClave(cursorClave);
        }

        return movimiento;
    }

    public Movimiento crearMovimientoDesdeCero(LocalDateTime fechaMovimiento,
                                               TipoMovimiento tipoMovimiento,
                                               FormatoMovimiento formatoMovimiento,
                                               String concepto,
                                               BigDecimal cantidad,
                                               boolean seDebeDevolver,
                                               boolean conFechaDevuelta,
                                               LocalDateTime fechaDevuelta,){

        Movimiento movimiento;

        movimiento = new Movimiento(fechaMovimiento,
                tipoMovimiento,
                formatoMovimiento,
                concepto,
                cantidad,
                seDebeDevolver,
                conFechaDevuelta,
                fechaDevuelta,
                new AppVersion());

        return movimiento;

    }

}
