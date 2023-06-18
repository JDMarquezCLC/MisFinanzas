package com.josedavid.misfinanzas.actividades.pantallamovimientos;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.actividades.pantallainformes.PantallaInformes;
import com.josedavid.misfinanzas.actividades.pantallainformes.creadorlista.CreadorListaSegunTiempo;
import com.josedavid.misfinanzas.actividades.pantallainformes.creadorlista.TipoTiempo;
import com.josedavid.misfinanzas.actividades.pantallainsertaractividad.PantallaInsertarMovimiento;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.actividades.pantallaprincipal.PantallaPrincipal;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.FormatoMovimiento;
import com.josedavid.misfinanzas.bbdd.movimientos.tipos.TipoMovimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;
import com.josedavid.misfinanzas.otros.adaptadores.AdaptadorMovimiento;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PantallaMovimientos extends AppCompatActivity {

    private Usuario usuarioSeleccionado;
    private ArrayList<Movimiento> listaMovimientos;
    private Intent intent;
    private ListView viewListaMovimientos;
    private AdaptadorMovimiento adaptadorMovimiento;
    private TextView textoPantallaMovimientos;
    private LocalDateTime fechaSeleccionada;
    private Button botonInsertarMovimiento;
    private GestorUsuario gestorUsuario;
    private Gestor gestor;
    private int valorTipoTiempo;
    private Toaster toaster;
    private TextView textoCantidadMovimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_movimientos);

        inicializar();

        botonInsertarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad;
                actividad = new Intent(
                        PantallaMovimientos.this,
                        PantallaInsertarMovimiento.class);
                actividad.putExtra("usuario",usuarioSeleccionado);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    actividad.putExtra("fecha", fechaSeleccionada.toString());
                }
                startActivity(actividad);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recargarUsuario();
        establecerVisual();
    }

    private void recargarUsuario(){
        usuarioSeleccionado = gestorUsuario.obtenerUsuarioPorClave(usuarioSeleccionado.getClave());
        usuarioSeleccionado.setListaMovimientos(
                gestor.getListaMovimientosUsuario(
                        usuarioSeleccionado.getNombre()));
        listaMovimientos = usuarioSeleccionado.getListaMovimientos();
    }

    private void inicializar(){
        establecerCosas();
        establecerVisual();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                menuInfo;
        inflater.inflate(R.menu.menu_movimientos,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.menuMovsBorrar:
                gestor.eliminarMovimientoUsuario(usuarioSeleccionado,listaMovimientos.get(info.position));
                recargarUsuario();
                establecerVisual();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_mandarmail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menuEnviarcorreo:
                Intent intent = new Intent(Intent.ACTION_SENDTO);

                if (!listaMovimientos.isEmpty()){
                    String cuerpoAMandar = getResources().getString(R.string.informeDelDia);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        cuerpoAMandar = cuerpoAMandar + " " +
                                fechaSeleccionada.getDayOfMonth() + "/" +
                                fechaSeleccionada.getMonthValue() + "/" +
                                fechaSeleccionada.getYear() + "\n";
                    }

                    intent.putExtra(Intent.EXTRA_SUBJECT,cuerpoAMandar);

                    cuerpoAMandar = cuerpoAMandar + "\nUsuario: " + usuarioSeleccionado.getNombre();
                    cuerpoAMandar = cuerpoAMandar + "\n\nNúmero de movimientos: " + listaMovimientos.size();

                    for (int i = 0;i< listaMovimientos.size();i++){
                        Movimiento movimiento = listaMovimientos.get(i);
                        String numeroMovimiento = "Movimiento Nº" + movimiento.getClave();
                        cuerpoAMandar = cuerpoAMandar + "\n\n" + numeroMovimiento;
                        String linea = "";
                        for (int y=0;y<numeroMovimiento.length();y++){
                            linea = linea + "- ";
                        }
                        cuerpoAMandar = cuerpoAMandar + "\n" + linea;
                        cuerpoAMandar = cuerpoAMandar + "\nConcepto: " + movimiento.getConcepto();
                        cuerpoAMandar = cuerpoAMandar + "\nTipo: ";
                        if (movimiento.getTipo().equals(TipoMovimiento.GASTO)){
                            cuerpoAMandar = cuerpoAMandar + "Gasto";
                        } else{
                            cuerpoAMandar = cuerpoAMandar + "Ganancia";
                        }
                        cuerpoAMandar = cuerpoAMandar + "\nFormato: ";
                        if (movimiento.getFormato().equals(FormatoMovimiento.METALICO)){
                            cuerpoAMandar = cuerpoAMandar + "Metálico";
                        } else{
                            cuerpoAMandar = cuerpoAMandar + "Digital";
                        }
                        cuerpoAMandar = cuerpoAMandar + "\nCantidad: " + movimiento.getCantidad().setScale(2, RoundingMode.HALF_EVEN) + "€";
                    }

                    intent.putExtra(Intent.EXTRA_TEXT,cuerpoAMandar);
                    intent.setData(Uri.parse("mailto:"));

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else{
                        toaster.mostrarToast("No se encontró ninguna aplicación con la que mandar el correo.",1);
                    }

                } else{
                    String textoAMostrar = getResources().getString(R.string.noPuedeMandarEmail);
                    toaster.mostrarToast(textoAMostrar,1);
                }
                break;
            case R.id.menuBorrarTodos:
                for (int i = 0;i< listaMovimientos.size();i++){
                    gestor.eliminarMovimientoUsuario(usuarioSeleccionado,listaMovimientos.get(i));
                }
                recargarUsuario();
                establecerVisual();
                break;
        }



        return true;
    }

    private void establecerVisual(){
        establecerListaMovimientos();
        adaptadorMovimiento.clear();
        adaptadorMovimiento.addAll(listaMovimientos);
        adaptadorMovimiento.notifyDataSetChanged();
        String textoTopNada = getResources().getString(R.string.no_movs_registrados);
        String textoMovsRegistrados = getResources().getString(R.string.movsRegistrados);
        if (listaMovimientos.isEmpty()){
            this.textoCantidadMovimientos.setText(textoTopNada);
        } else{
            this.textoCantidadMovimientos.setText(listaMovimientos.size() + " " + textoMovsRegistrados);
        }
        String fecha = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fecha = fechaSeleccionada.getDayOfMonth() + "/" + fechaSeleccionada.getMonthValue() + "/" + fechaSeleccionada.getYear();
        }
        this.textoPantallaMovimientos.setText(fecha);

    }

    private void establecerListaMovimientos(){
        CreadorListaSegunTiempo creadorListaSegunTiempo = new CreadorListaSegunTiempo();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.listaMovimientos = creadorListaSegunTiempo.devolverMovimientosSegunTiempo(
                    usuarioSeleccionado.getListaMovimientos(),
                    TipoTiempo.getTipoTiempoByValue(valorTipoTiempo),
                    intent.getIntExtra("cantidad",0),
                    this.fechaSeleccionada);
        }
    }


    private void establecerCosas(){
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.viewListaMovimientos = this.findViewById(R.id.pantallaMovimientosListView);

        this.valorTipoTiempo = intent.getIntExtra("valor",8);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.fechaSeleccionada = LocalDateTime.parse(intent.getStringExtra("fecha"));
        }

        establecerListaMovimientos();



        this.adaptadorMovimiento = new AdaptadorMovimiento(listaMovimientos,this);
        this.viewListaMovimientos.setAdapter(adaptadorMovimiento);
        this.textoPantallaMovimientos = this.findViewById(R.id.textoPantallaMovimientos);
        this.botonInsertarMovimiento = this.findViewById(R.id.botonInsertarMovimientoDiaEspecifico);
        gestorUsuario = new GestorUsuario(
                PantallaMovimientos.this.getApplicationContext(),
                new Toaster(PantallaMovimientos.this.getApplicationContext()));
        this.gestor = new Gestor(PantallaMovimientos.this.getApplicationContext());
        this.toaster = new Toaster(PantallaMovimientos.this.getApplicationContext());
        this.textoCantidadMovimientos = this.findViewById(R.id.textoPantallaMovimientoscantidad);
        registerForContextMenu(viewListaMovimientos);
    }
}
