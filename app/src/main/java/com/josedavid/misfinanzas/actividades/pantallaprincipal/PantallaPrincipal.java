package com.josedavid.misfinanzas.actividades.pantallaprincipal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.actividades.lanzadores.principalofirstlogin.PrincipalOFirstLogin;
import com.josedavid.misfinanzas.actividades.pantallaconfiguser.PantallaConfigUsuario;
import com.josedavid.misfinanzas.actividades.pantallafirstlog.PantallaFirstLogin;
import com.josedavid.misfinanzas.actividades.pantallainformes.PantallaInformes;
import com.josedavid.misfinanzas.actividades.pantallainsertaractividad.PantallaInsertarMovimiento;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {

    private Toaster toaster;
    private GestorUsuario gestorUsuario;
    private Intent intent;
    private Usuario usuarioSeleccionado;
    private Gestor gestor;
    private ArrayList<Movimiento> listaMovimientos;
    private TextView textoBienvenido;
    private Button botonInsertarMovimiento;
    private Button botonInformes;
    private TextView textoAhoraTienes;
    private String valorTextoAhoraTienes;
    private String valorTextoBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_principal);

        inicializar();

        botonInsertarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;
                actividad = new Intent(
                        PantallaPrincipal.this,
                        PantallaInsertarMovimiento.class);
                actividad.putExtra("usuario",usuarioSeleccionado);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    actividad.putExtra("fecha", LocalDateTime.now().toString());
                }
                startActivity(actividad);
            }
        });

        botonInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;
                actividad = new Intent(
                        PantallaPrincipal.this,
                        PantallaInformes.class);
                actividad.putExtra("usuario",usuarioSeleccionado);
                startActivity(actividad);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarioSeleccionado = gestorUsuario.obtenerUsuarioPorClave(usuarioSeleccionado.getClave());
        usuarioSeleccionado.setListaMovimientos(
                gestor.getListaMovimientosUsuario(
                        usuarioSeleccionado.getNombre()));
        prepararVisual();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent actividad;
        actividad = new Intent(
                PantallaPrincipal.this,
                PantallaConfigUsuario.class);
        actividad.putExtra("usuario",usuarioSeleccionado);
        startActivity(actividad);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_opciones_usuario,menu);
        return true;
    }

    private void inicializar(){
        establecerCosas();
        prepararVisual();
    }

    private void establecerCosas(){
        toaster = new Toaster(PantallaPrincipal.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(
                PantallaPrincipal.this.getApplicationContext(),
                toaster);
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.gestor = new Gestor(PantallaPrincipal.this.getApplicationContext());
        this.listaMovimientos = usuarioSeleccionado.getListaMovimientos();
        this.textoBienvenido = this.findViewById(R.id.pantallaPrincipalBienvenido);
        this.botonInformes = this.findViewById(R.id.pantallaPrincipalVerMovimientos);
        this.botonInsertarMovimiento = this.findViewById(R.id.pantallaPrincipalNuevoMov);
        this.textoAhoraTienes = this.findViewById(R.id.pantallaPrincipalAhoraTienes);
        this.valorTextoAhoraTienes = (String) this.textoAhoraTienes.getText();
        this.valorTextoBienvenida = (String) this.textoBienvenido.getText();
    }

    private void prepararVisual(){
        CharSequence stringTextoBienvenido = textoBienvenido.getText();
        String textoDeBienvenida = stringTextoBienvenido + usuarioSeleccionado.getNombre();
        textoBienvenido.setText(valorTextoBienvenida + " " + usuarioSeleccionado.getNombre());
        BigDecimal dineroTotal = usuarioSeleccionado.getDineroTotal().setScale(2, RoundingMode.HALF_EVEN);
        textoAhoraTienes.setText(valorTextoAhoraTienes + " " + dineroTotal + "€");
    }

}
