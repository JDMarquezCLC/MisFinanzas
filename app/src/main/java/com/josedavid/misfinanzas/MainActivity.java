package com.josedavid.misfinanzas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.josedavid.misfinanzas.actividades.lanzadores.principalofirstlogin.PrincipalOFirstLogin;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.actividades.pantallalogin.ModoLogin;
import com.josedavid.misfinanzas.otros.version.AppVersion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView textoTitulo;
    private String tituloApp;
    private GestorUsuario gestorUsuario;
    private ModoLogin modoLogin;
    private Button botonIniciarSesion;
    private Button botonCrearUsuario;
    private EditText editTextNombreUsuario;
    private EditText editTextPasswordUsuario;
    private Toaster toaster;
    private Gestor gestor;
    private PrincipalOFirstLogin principalOFirstLogin;
    private Intent actividad;
    private AppVersion appVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarTodo();

        botonCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombreUsuarioNuevo = String.valueOf(editTextNombreUsuario.getText());
                String passUsuarioNuevo = String.valueOf(editTextPasswordUsuario.getText());
                boolean encontrado = false;
                encontrado = gestorUsuario.encontrarSiUsuarioExiste(nombreUsuarioNuevo);
                if (!encontrado){
                    gestorUsuario.crearUsuario(nombreUsuarioNuevo,passUsuarioNuevo);
                    iniciarSesion();
                } else{
                    String textoAMostrar = getResources().getString(R.string.yaExisteUsuario);
                    toaster.mostrarToast(textoAMostrar,0);
                }


            }
        });

        botonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

    }

    private void iniciarSesion(){
        String nombreUsuarioABuscar = String.valueOf(editTextNombreUsuario.getText());
        if (nombreUsuarioABuscar.equals("") || nombreUsuarioABuscar == null){
            toaster.mostrarToast("Debes introducir un usuario.",1);
        } else {
            boolean encontrado = false;
            encontrado = gestorUsuario.encontrarSiUsuarioExiste(nombreUsuarioABuscar);

            if (encontrado){
                ArrayList<Movimiento> listaMovimientos = gestor.getListaMovimientosUsuario(
                        nombreUsuarioABuscar);
                Usuario usuario = gestorUsuario.buscarUsuario(nombreUsuarioABuscar);
                if (usuario.getPassword().equals(String.valueOf(editTextPasswordUsuario.getText()))){
                    usuario.setListaMovimientos(listaMovimientos);
                    actividad = principalOFirstLogin.lanzarSegunSiEsFirstLoginONo(
                            usuario,MainActivity.this);
                    startActivity(actividad);
                } else{
                    String textoAMostrar = getResources().getString(R.string.badPassword);
                    toaster.mostrarToast(textoAMostrar,1);
                }
            } else{
                toaster.mostrarToast("Este usuario no existe.",1);
            }
        }
    }

    private void inicializarTodo(){
        //this.deleteDatabase("DBMisFinanzas");
        cargarVariables();
        cargarViews();
        establecerCosas();
    }

    private void cargarVariables(){
        this.tituloApp = "Mis Finanzas";
    }

    private void cargarViews(){
        //this.textoTitulo = this.findViewById(R.id.loginTituloApp);
        this.botonCrearUsuario = this.findViewById(R.id.loginBtnCrearUsuario);
        this.botonIniciarSesion = this.findViewById(R.id.loginBtnIniciarSesion);
        this.editTextNombreUsuario = this.findViewById(R.id.loginEditTextNombre);
        this.editTextPasswordUsuario = this.findViewById(R.id.loginEditTextPasswd);
    }

    private void establecerCosas(){
        //this.textoTitulo.setText(this.tituloApp);
        this.toaster = new Toaster(MainActivity.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(MainActivity.this.getApplicationContext(),this.toaster);
        this.gestor = new Gestor(MainActivity.this.getApplicationContext());
        this.principalOFirstLogin = new PrincipalOFirstLogin();
        this.appVersion = new AppVersion();
    }
}