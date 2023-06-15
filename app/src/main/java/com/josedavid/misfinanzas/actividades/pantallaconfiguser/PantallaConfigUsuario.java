package com.josedavid.misfinanzas.actividades.pantallaconfiguser;

import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.ConversorCantidad;
import com.josedavid.misfinanzas.bbdd.gestor.conversorcantidad.contardecimales.ContadorDecimales;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PantallaConfigUsuario extends AppCompatActivity {

    private Intent intent;
    private Usuario usuarioSeleccionado;
    private GestorUsuario gestorUsuario;
    private Toaster toaster;
    private Gestor gestor;
    private Button botonActualizar;
    private EditText editTextNombre;
    private EditText editTextPass;
    private RadioButton botonGanancia;
    private RadioButton botonGasto;
    private RadioButton botonMetalico;
    private RadioButton botonDigital;
    private TextView texto;
    private LocalDateTime fechaPasada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_config_usuario);

        inicializar();

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String errorAMostrar = "";

                boolean nombreEscrito = false;
                boolean passEscrito = false;


                if (editTextPass.getText().length()>0){
                    passEscrito = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.introducePass);
                }
                if (editTextNombre.getText().length()>0){
                    nombreEscrito = true;
                } else{
                    errorAMostrar = getResources().getString(R.string.introduceNombre);
                }

                if (nombreEscrito && passEscrito){
                    String nombre = String.valueOf(editTextNombre.getText());
                    String pass = String.valueOf(editTextPass.getText());

                    gestor.actualizarNombreUsuario(nombre,usuarioSeleccionado);
                    gestor.actualizarPassUsuario(pass,usuarioSeleccionado);

                    finish();
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
        toaster = new Toaster(PantallaConfigUsuario.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(
                PantallaConfigUsuario.this.getApplicationContext(),
                toaster);
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.gestor = new Gestor(PantallaConfigUsuario.this.getApplicationContext());
        this.editTextPass = this.findViewById(R.id.configUserPass);
        this.editTextNombre = this.findViewById(R.id.configUserNombre);
        this.botonActualizar = this.findViewById(R.id.configScreenActualizar);
        this.texto = this.findViewById(R.id.configScreenTexto);
        String textoAMostrar = getResources().getString(R.string.config_usuario);
        this.texto.setText(textoAMostrar + " " + usuarioSeleccionado.getNombre());
    }



}
