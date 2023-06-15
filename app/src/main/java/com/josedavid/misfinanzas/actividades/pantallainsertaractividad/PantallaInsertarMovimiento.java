package com.josedavid.misfinanzas.actividades.pantallainsertaractividad;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.ConversorCantidad;
import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.contardecimales.ContadorDecimales;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PantallaInsertarMovimiento extends AppCompatActivity {

    private Intent intent;
    private Usuario usuarioSeleccionado;
    private GestorUsuario gestorUsuario;
    private Toaster toaster;
    private ArrayList<Movimiento> listaMovimientos;
    private Gestor gestor;
    private Button botonInsertar;
    private EditText editTextConcepto;
    private EditText editTextCantidad;
    private RadioGroup grupoFormato;
    private RadioGroup grupoTipo;
    private RadioButton botonGanancia;
    private RadioButton botonGasto;
    private RadioButton botonMetalico;
    private RadioButton botonDigital;
    private TextView textoFechaHoy;
    private LocalDateTime fechaPasada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_insertar_movimiento);

        inicializar();

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String errorAMostrar = "";

                boolean tipoElegido = false;
                boolean formatoElegido = false;
                boolean conceptoEscrito = false;
                boolean cantidadEscrita = false;


                if (editTextCantidad.getText().length()>0){
                    cantidadEscrita = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.introduceCantidad);
                }
                if (editTextConcepto.getText().length()>0){
                    conceptoEscrito = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.introduceConcepto);
                }
                if (botonMetalico.isChecked() || botonDigital.isChecked()){
                    formatoElegido = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.eligeMetalicoODigital);
                }
                if (botonGanancia.isChecked() || botonGasto.isChecked()){
                    tipoElegido = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.eligeGananciaOGasto);
                }

                if (tipoElegido && formatoElegido && conceptoEscrito && cantidadEscrita){
                    BigDecimal cantidad;
                    ConversorCantidad conversorCantidad = new ConversorCantidad();
                    cantidad = conversorCantidad.convertirTextFieldABigDecimal(editTextCantidad);

                    ContadorDecimales contadorDecimales = new ContadorDecimales();

                    if(contadorDecimales.contarDecimalesNumeroEnString(cantidad.toString())<3){
                        Movimiento movimiento;

                        LocalDateTime fechaHoy = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            fechaHoy = fechaPasada;
                        }
                        TipoMovimiento tipoMovimiento = null;
                        FormatoMovimiento formatoMovimiento = null;
                        String concepto;


                        if (botonGanancia.isChecked()){
                            tipoMovimiento = TipoMovimiento.GANANCIA;
                        }
                        if (botonGasto.isChecked()){
                            tipoMovimiento = TipoMovimiento.GASTO;
                        }
                        if (botonMetalico.isChecked()){
                            formatoMovimiento = FormatoMovimiento.METALICO;
                        }
                        if (botonDigital.isChecked()){
                            formatoMovimiento = FormatoMovimiento.DIGITAL;
                        }

                        concepto = String.valueOf(editTextConcepto.getText());


                        movimiento = new Movimiento(
                                fechaHoy,
                                tipoMovimiento,
                                formatoMovimiento,
                                concepto,
                                cantidad);

                        usuarioSeleccionado = gestor.insertarNuevaEntradaTablaUsuario(
                                usuarioSeleccionado,
                                movimiento);

                        SoundPool soundPool = new SoundPool.Builder().build();
                        int sonido;

                        if(movimiento.getTipo().equals(TipoMovimiento.GASTO)){
                            sonido = soundPool.load(
                                    PantallaInsertarMovimiento.this.getApplicationContext(),
                                    R.raw.paper,
                                    1);
                        } else{
                            sonido = soundPool.load(
                                    PantallaInsertarMovimiento.this.getApplicationContext(),
                                    R.raw.cash,
                                    1);
                        }

                        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                                soundPool.play(sonido,0.99f, 0.99f, 1, 0, 0.99f);
                            }
                        });

                        finish();
                    } else{
                        String textoAMostrar = getResources().getString(R.string.noMasDe2Decimales);
                        toaster.mostrarToast(textoAMostrar,1);
                    }
                } else{
                    toaster.mostrarToast(errorAMostrar,1);
                }


            }
        });



    }



    private void inicializar(){
        establecerCosas();
    }

    private void establecerCosas(){
        toaster = new Toaster(PantallaInsertarMovimiento.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(
                PantallaInsertarMovimiento.this.getApplicationContext(),
                toaster);
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.gestor = new Gestor(PantallaInsertarMovimiento.this.getApplicationContext());
        this.listaMovimientos = usuarioSeleccionado.getListaMovimientos();
        this.editTextCantidad = this.findViewById(R.id.mainScreenEditTextCantidad);
        this.editTextConcepto = this.findViewById(R.id.mainScreenEditTextConcepto);
        this.grupoFormato = this.findViewById(R.id.mainScreenRadiogroupFormatos);
        this.grupoTipo = this.findViewById(R.id.mainScreenRadiogroupTipo);
        this.botonInsertar = this.findViewById(R.id.mainScreenBotonInsertar);
        this.botonGanancia = this.findViewById(R.id.mainScreenInsertarMovGanancia);
        this.botonGasto = this.findViewById(R.id.mainScreenInsertarMovGasto);
        this.botonMetalico = this.findViewById(R.id.mainScreenFormatoMetalico);
        this.botonDigital = this.findViewById(R.id.mainScreenFormatoDigital);
        this.textoFechaHoy = this.findViewById(R.id.textoFechaPrincipal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.fechaPasada = LocalDateTime.parse(intent.getStringExtra("fecha"));
        }
        this.textoFechaHoy.setText(establecerFechaHoy());
    }

    private CharSequence establecerFechaHoy(){
        CharSequence retorno;
        String fecha = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            fecha = fechaPasada.getDayOfMonth() + "/" + fechaPasada.getMonthValue() + "/" + fechaPasada.getYear();
        }
        retorno = fecha;
        return retorno;
    }

}
