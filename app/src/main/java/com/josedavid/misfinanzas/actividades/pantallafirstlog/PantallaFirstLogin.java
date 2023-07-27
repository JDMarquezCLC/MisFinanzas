package com.josedavid.misfinanzas.actividades.pantallafirstlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.actividades.lanzadores.principalofirstlogin.PrincipalOFirstLogin;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.ConversorCantidad;
import com.josedavid.misfinanzas.bbdd.gestor.gestormovimientos.creadormovimiento.CreadorMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;
import com.josedavid.misfinanzas.otros.version.AppVersion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PantallaFirstLogin extends AppCompatActivity {
    private Intent intent;
    private Usuario usuarioSeleccionado;
    private GestorUsuario gestorUsuario;
    private Toaster toaster;
    private ArrayList<Movimiento> listaMovimientos;
    private Gestor gestor;
    private Button botonListo;
    private EditText editTextMetalico;
    private EditText editTextDigital;
    private PrincipalOFirstLogin principalOFirstLogin;
    private Intent actividadALanzar;
    private TextView textoBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_primerlogin);

        inicializar();

        botonListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextDigital.getText().length()>0 && editTextMetalico.getText().length()>0){
                    insertarPrimerMovimiento();
                } else{
                    toaster.mostrarToast("Por favor, no dejes ningún campo vacío.",1);
                }

            }
        });



    }

    private void insertarPrimerMovimiento(){
        LocalDateTime fechaHoy = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fechaHoy = LocalDateTime.now();
        }
        TipoMovimiento ganancia = TipoMovimiento.GANANCIA;
        FormatoMovimiento formatoMovimientoMetalico = FormatoMovimiento.METALICO;
        FormatoMovimiento formatoMovimientoDigital = FormatoMovimiento.DIGITAL;
        ConversorCantidad conversorCantidad = new ConversorCantidad();
        BigDecimal cantidadMetalico = conversorCantidad.convertirTextFieldABigDecimal(
                editTextMetalico);
        BigDecimal cantidadDigital = conversorCantidad.convertirTextFieldABigDecimal(
                editTextDigital);


        CreadorMovimiento creadorMovimiento = new CreadorMovimiento();

        Movimiento movimientoMetalico = creadorMovimiento.crearMovimientoDesdeBBDD(null,)
                = new Movimiento(null,fechaHoy,
                ganancia,
                formatoMovimientoMetalico,
                "Dinero metálico inicial",
                cantidadMetalico,
                false,
                false,
                null,
                new AppVersion());

        Movimiento movimientoDigital = new Movimiento(null,fechaHoy,
                ganancia,
                formatoMovimientoDigital,
                "Dinero digital inicial",
                cantidadDigital,
                false,
                false,
                null,
                new AppVersion());

        usuarioSeleccionado = gestor.insertarNuevaEntradaTablaUsuario(
                usuarioSeleccionado,
                movimientoMetalico);
        //usuarioSeleccionado = gestorUsuario.obtenerUsuarioPorClave(usuarioSeleccionado.getClave());
        usuarioSeleccionado = gestor.insertarNuevaEntradaTablaUsuario(
                usuarioSeleccionado,
                movimientoDigital);

        gestor.actualizarUsuarioPrimerMovimientoHecho(usuarioSeleccionado.getNombre());
        usuarioSeleccionado = gestorUsuario.obtenerUsuarioPorClave(usuarioSeleccionado.getClave());
        actividadALanzar = principalOFirstLogin.lanzarSegunSiEsFirstLoginONo(
                usuarioSeleccionado, PantallaFirstLogin.this);
        startActivity(actividadALanzar);
        finish();
    }


    private void inicializar(){
        establecerCosas();
        prepararVisual();
    }

    private void establecerCosas(){
        toaster = new Toaster(PantallaFirstLogin.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(
                PantallaFirstLogin.this.getApplicationContext(),
                toaster);
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.gestor = new Gestor(PantallaFirstLogin.this.getApplicationContext());
        this.listaMovimientos = gestor.getListaMovimientosUsuario(usuarioSeleccionado.getNombre());
        this.botonListo = this.findViewById(R.id.firstLoginBotonListo);
        this.editTextDigital = this.findViewById(R.id.firstLoginDigitalET);
        this.editTextMetalico = this.findViewById(R.id.firstLoginMetalET);
        this.principalOFirstLogin = new PrincipalOFirstLogin();
        this.textoBienvenido = this.findViewById(R.id.firstLoginBienvenido);
    }

    private void prepararVisual(){
        CharSequence stringTextoBienvenido = textoBienvenido.getText();
        String textoDeBienvenida = stringTextoBienvenido + usuarioSeleccionado.getNombre();
        textoBienvenido.setText(textoDeBienvenida);
    }
}
