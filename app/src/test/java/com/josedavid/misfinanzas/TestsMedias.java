package com.josedavid.misfinanzas;

import com.josedavid.misfinanzas.actividades.utilidades.informes.media.EstablecedorMediasYTotales;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TestsMedias {

    @Test
    public void comprobarQueDevuelvenBuenasMedias(){
        EstablecedorMediasYTotales establecedorMediasYTotales;
        establecedorMediasYTotales = new EstablecedorMediasYTotales();

        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
        Movimiento movimiento = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GANANCIA,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        5));
        Movimiento movimiento2 = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GANANCIA,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        10));
        Movimiento movimiento3 = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GANANCIA,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        38));
        listaMovimientos.add(movimiento);
        listaMovimientos.add(movimiento2);
        listaMovimientos.add(movimiento3);

        ArrayList<BigDecimal> medias = establecedorMediasYTotales.establecerMedia(listaMovimientos).get(0);
        ArrayList<BigDecimal> mediasEsperadas = new ArrayList<>();
        mediasEsperadas.add(new BigDecimal("17.67"));
        mediasEsperadas.add(new BigDecimal("17.67"));
        mediasEsperadas.add(new BigDecimal("17.67"));
        mediasEsperadas.add(new BigDecimal("0"));
        mediasEsperadas.add(new BigDecimal("0"));
        mediasEsperadas.add(new BigDecimal("0"));

        Assert.assertEquals(mediasEsperadas,medias);

    }

    @Test
    public void comprobarMediaGastos(){
        EstablecedorMediasYTotales establecedorMediasYTotales;
        establecedorMediasYTotales = new EstablecedorMediasYTotales();

        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
        Movimiento movimiento = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GASTO,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        5));
        Movimiento movimiento2 = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GASTO,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        10));
        Movimiento movimiento3 = new Movimiento(
                0,
                LocalDateTime.now().minusDays(5),
                TipoMovimiento.GASTO,
                FormatoMovimiento.METALICO,
                "",
                new BigDecimal(
                        38));
        listaMovimientos.add(movimiento);
        listaMovimientos.add(movimiento2);
        listaMovimientos.add(movimiento3);

        ArrayList<BigDecimal> medias = establecedorMediasYTotales.establecerMedia(listaMovimientos).get(0);
        ArrayList<BigDecimal> mediasEsperadas = new ArrayList<>();
        mediasEsperadas.add(new BigDecimal("0"));
        mediasEsperadas.add(new BigDecimal("0"));
        mediasEsperadas.add(new BigDecimal("0"));
        mediasEsperadas.add(new BigDecimal("17.67"));
        mediasEsperadas.add(new BigDecimal("17.67"));
        mediasEsperadas.add(new BigDecimal("17.67"));

        Assert.assertEquals(mediasEsperadas,medias);
    }

}
