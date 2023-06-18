package com.josedavid.misfinanzas.otros.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class AdaptadorMovimiento extends ArrayAdapter<Movimiento> {
    private ArrayList<Movimiento> movimientos;

    public AdaptadorMovimiento(ArrayList<Movimiento> movimientos, Context context) {
        super(context,R.layout.layout_movimiento,movimientos);
        this.movimientos = movimientos;
    }



    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View movimiento = mostrado.inflate(R.layout.layout_movimiento, viewGroup, false);

        TextView textConcepto = movimiento.findViewById(R.id.conceptoAdapterMovimiento);
        TextView textCantidad = movimiento.findViewById(R.id.cantidadAdapterMovimiento);
        //TextView textFecha = movimiento.findViewById(R.id.fechaAdapterMovimiento);
        TextView textFormato = movimiento.findViewById(R.id.formatoAdapterMovimiento);

        textConcepto.setText(movimientos.get(posicion).getConcepto());
        BigDecimal cantidadBD = movimientos.get(posicion).getCantidad().setScale(2, RoundingMode.HALF_EVEN);
        textCantidad.setText(cantidadBD.toString() + "€");
        //textFecha.setText(movimientos.get(posicion).getFecha().toString());

        if (movimientos.get(posicion).getTipo() == TipoMovimiento.GASTO){
            movimiento.setBackgroundResource(R.color.rojogasto);
            textCantidad.setBackgroundResource(R.color.rojogasto);
            textConcepto.setBackgroundResource(R.color.rojogasto);
            textFormato.setBackgroundResource(R.color.rojogasto);
            //textFecha.setBackgroundResource(R.color.rojogasto);
        } else if (movimientos.get(posicion).getTipo() == TipoMovimiento.GANANCIA){
            movimiento.setBackgroundResource(R.color.verdeganancia);
            textCantidad.setBackgroundResource(R.color.verdeganancia);
            textConcepto.setBackgroundResource(R.color.verdeganancia);
            textFormato.setBackgroundResource(R.color.verdeganancia);
            //textFecha.setBackgroundResource(R.color.verdeganancia);
        }

        String textoFormato;

        if (movimientos.get(posicion).getFormato()== FormatoMovimiento.METALICO){
            textoFormato = super.getContext().getResources().getString(R.string.metalico);
        } else{
            textoFormato = super.getContext().getResources().getString(R.string.digital);
        }

        textFormato.setText(textoFormato);


        return movimiento;
    }
}
