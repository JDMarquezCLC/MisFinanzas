package com.josedavid.misfinanzas.actividades.pantallainformes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.R;
import com.josedavid.misfinanzas.actividades.pantallainformes.convertirbdaint.ConvertirBDAInt;
import com.josedavid.misfinanzas.actividades.pantallainformes.tipos.TipoMedia;
import com.josedavid.misfinanzas.actividades.pantallainformes.tipos.TipoTiempo;
import com.josedavid.misfinanzas.actividades.pantallainformes.tipos.TipoTotal;
import com.josedavid.misfinanzas.actividades.pantallainformes.tipos.TipoTotalMovs;
import com.josedavid.misfinanzas.actividades.pantallalogin.GestorUsuario;
import com.josedavid.misfinanzas.actividades.pantallamovimientos.PantallaMovimientos;
import com.josedavid.misfinanzas.actividades.utilidades.informes.media.EstablecedorMediasYTotales;
import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;

public class PantallaInformes extends AppCompatActivity {
    private Intent intent;
    private Usuario usuarioSeleccionado;
    private GestorUsuario gestorUsuario;
    private Toaster toaster;
    private ArrayList<Movimiento> listaMovimientos;
    private Gestor gestor;
    private CalendarView calendarView;
    private Button botonMes;
    private Button boton6Mes;
    private Button botonYear;
    private Button botonDia;
    private LocalDateTime fechaSeleccionada;
    private EnumMap<TipoMedia,BigDecimal> listaMedias;
    private EnumMap<TipoTotal,BigDecimal> listaTotales;
    private EnumMap<TipoTotalMovs, Integer> listaTotalMovs;

/*    private BigDecimal mediaGastoDia;
    private BigDecimal mediaGastoMes;
    private BigDecimal mediaGananciaDia;
    private BigDecimal mediaGananciaMes;
    private BigDecimal mediaGasto6Mes;
    private BigDecimal mediaGanancia6Mes;
    private BigDecimal mediaGastoYear;
    private BigDecimal mediaGananciaYear;*/
    private TextView textoMediaGasto;
    private TextView textoMediaGanancia;
    private String textoMediaGastoDefecto;
    private String textoMediaGananciaDefecto;
    private Button botonVerMovimientos;
    private TextView textoTotalMovimientos;
    private TextView textoTotalGanancias;
    private TextView textoTotalGastos;
    private TipoTiempo tiempoElegido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_informes);

        inicializar();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(
                    @NonNull CalendarView calendarView,
                    int year,
                    int month,
                    int day) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    fechaSeleccionada = LocalDateTime.of(year,month+1,day,0,0);
                    establecerMediasYTotales();
                    establecerVisualSegunTiempo();

                }

            }
        });


        botonDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiempoElegido = TipoTiempo.DIA;
                establecerVisualSegunTiempo();
            }
        });

        botonMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiempoElegido = TipoTiempo.MES;
                establecerVisualSegunTiempo();
            }
        });
        boton6Mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiempoElegido = TipoTiempo.SEISMES;
                establecerVisualSegunTiempo();
            }
        });
        botonYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiempoElegido = TipoTiempo.YEAR;
                establecerVisualSegunTiempo();
            }
        });
        botonVerMovimientos.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent actividad;
                actividad = new Intent(
                        PantallaInformes.this,
                        PantallaMovimientos.class);
                actividad.putExtra("usuario",usuarioSeleccionado);
                actividad.putExtra("valor",0);
                actividad.putExtra("cantidad",1);
                actividad.putExtra("fecha",fechaSeleccionada.toString());
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
        this.listaMovimientos = usuarioSeleccionado.getListaMovimientos();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            establecerMediasYTotales();
        }
        establecerVisualSegunTiempo();
    }

    private void establecerVisualSegunTiempo(){
        switch(tiempoElegido){
            case DIA:
                establecerVisual(
                        listaMedias.get(TipoMedia.MEDIAGASTODIA),
                        listaMedias.get(TipoMedia.MEDIAGANANCIADIA),
                        listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGANANCIADIA) + listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGASTODIA),
                        listaTotales.get(TipoTotal.TOTALGASTODIA),
                        listaTotales.get(TipoTotal.TOTALGANANCIADIA));
                break;
            case MES:
                establecerVisual(
                        listaMedias.get(TipoMedia.MEDIAGASTOMES),
                        listaMedias.get(TipoMedia.MEDIAGANANCIAMES),
                        listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGANANCIAMES) + listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGASTOMES),
                        listaTotales.get(TipoTotal.TOTALGASTOMES),
                        listaTotales.get(TipoTotal.TOTALGANANCIAMES));
                break;
            case YEAR:
                establecerVisual(
                        listaMedias.get(TipoMedia.MEDIAGASTOYEAR),
                        listaMedias.get(TipoMedia.MEDIAGANANCIAYEAR),
                        listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGANANCIAYEAR) + listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGASTOYEAR),
                        listaTotales.get(TipoTotal.TOTALGASTOYEAR),
                        listaTotales.get(TipoTotal.TOTALGANANCIAYEAR));
                break;
            case SEISMES:
                establecerVisual(
                        listaMedias.get(TipoMedia.MEDIAGASTO6MES),
                        listaMedias.get(TipoMedia.MEDIAGANANCIA6MES),
                        listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGANANCIA6MES) + listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGASTO6MES),
                        listaTotales.get(TipoTotal.TOTALGASTO6MES),
                        listaTotales.get(TipoTotal.TOTALGANANCIA6MES));
                break;
            default:
                establecerVisual(
                        listaMedias.get(TipoMedia.MEDIAGASTOMES),
                        listaMedias.get(TipoMedia.MEDIAGANANCIAMES),
                        listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGANANCIAMES) + listaTotalMovs.get(TipoTotalMovs.TOTALMOVSGASTOMES),
                        listaTotales.get(TipoTotal.TOTALGASTOMES),
                        listaTotales.get(TipoTotal.TOTALGANANCIAMES));
                break;
        }
    }

    private void establecerVisual(
            BigDecimal mediaGasto,
            BigDecimal mediaGanancia,
            int totalMovimientos,
            BigDecimal totalGastos,
            BigDecimal totalGanancias){
        this.textoMediaGanancia.setText(textoMediaGananciaDefecto + mediaGanancia.setScale(2, RoundingMode.HALF_EVEN).toString() + "€");
        this.textoMediaGasto.setText(textoMediaGastoDefecto + mediaGasto.setScale(2, RoundingMode.HALF_EVEN).toString() + "€");

        //String textoTotalMovimientos1 = String.valueOf(R.string.totalMovimientos);
        String textoTotalMovimientos = getResources().getString(R.string.totalMovimientos) + " ";
        String textoTotalGanancias = getResources().getString(R.string.totalGanancias) + " ";
        String textoTotalGastos = getResources().getString(R.string.totalGastos) + " ";

        this.textoTotalMovimientos.setText(textoTotalMovimientos + totalMovimientos);
        this.textoTotalGanancias.setText(textoTotalGanancias + totalGanancias.setScale(2, RoundingMode.HALF_EVEN).toString() + "€");
        this.textoTotalGastos.setText(textoTotalGastos + totalGastos.setScale(2, RoundingMode.HALF_EVEN).toString() + "€");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void establecerMediasYTotales(){
        EstablecedorMediasYTotales establecedorMediasYTotales = new EstablecedorMediasYTotales();
        ConvertirBDAInt convertirBDAInt = new ConvertirBDAInt();
        ArrayList<ArrayList<BigDecimal>> listaRetorno = establecedorMediasYTotales.establecerMedia(listaMovimientos,fechaSeleccionada);
        ArrayList<BigDecimal> listaMedias = listaRetorno.get(0);
        ArrayList<BigDecimal> listaTotales = listaRetorno.get(1);
        ArrayList<Integer> listaTotalMovimientos = convertirBDAInt.convertirListaBDAInt(listaRetorno.get(2));

        this.listaMedias.put(TipoMedia.MEDIAGANANCIAMES,listaMedias.get(0));
        this.listaMedias.put(TipoMedia.MEDIAGANANCIA6MES,listaMedias.get(1));
        this.listaMedias.put(TipoMedia.MEDIAGANANCIAYEAR,listaMedias.get(2));
        this.listaMedias.put(TipoMedia.MEDIAGANANCIADIA,listaMedias.get(6)); // El de los días fue añadido después
        this.listaMedias.put(TipoMedia.MEDIAGASTOMES,listaMedias.get(3));
        this.listaMedias.put(TipoMedia.MEDIAGASTO6MES,listaMedias.get(4));
        this.listaMedias.put(TipoMedia.MEDIAGASTOYEAR,listaMedias.get(5));
        this.listaMedias.put(TipoMedia.MEDIAGASTODIA,listaMedias.get(7));

        this.listaTotales.put(TipoTotal.TOTALGANANCIADIA,listaTotales.get(0));
        this.listaTotales.put(TipoTotal.TOTALGANANCIAMES,listaTotales.get(1));
        this.listaTotales.put(TipoTotal.TOTALGANANCIA6MES,listaTotales.get(2));
        this.listaTotales.put(TipoTotal.TOTALGANANCIAYEAR,listaTotales.get(3));
        this.listaTotales.put(TipoTotal.TOTALGASTODIA,listaTotales.get(4));
        this.listaTotales.put(TipoTotal.TOTALGASTOMES,listaTotales.get(5));
        this.listaTotales.put(TipoTotal.TOTALGASTO6MES,listaTotales.get(6));
        this.listaTotales.put(TipoTotal.TOTALGASTOYEAR,listaTotales.get(7));

        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGANANCIADIA,listaTotalMovimientos.get(0));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGANANCIAMES,listaTotalMovimientos.get(1));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGANANCIA6MES,listaTotalMovimientos.get(2));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGANANCIAYEAR,listaTotalMovimientos.get(3));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGASTODIA,listaTotalMovimientos.get(4));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGASTOMES,listaTotalMovimientos.get(5));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGASTO6MES,listaTotalMovimientos.get(6));
        this.listaTotalMovs.put(TipoTotalMovs.TOTALMOVSGASTOYEAR,listaTotalMovimientos.get(7));
    }



    private void inicializar(){
        establecerCosas();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            establecerMediasYTotales();
        }
        establecerVisualSegunTiempo();
    }

    private void establecerCosas(){
        toaster = new Toaster(PantallaInformes.this.getApplicationContext());
        gestorUsuario = new GestorUsuario(
                PantallaInformes.this.getApplicationContext(),
                toaster);
        intent = getIntent();
        usuarioSeleccionado = (Usuario) intent.getSerializableExtra("usuario");
        this.gestor = new Gestor(PantallaInformes.this.getApplicationContext());
        this.listaMovimientos = usuarioSeleccionado.getListaMovimientos();
        this.calendarView = this.findViewById(R.id.pantallaInformesCalendario);
        this.botonMes = this.findViewById(R.id.pantallaInformesBotonMes);
        this.boton6Mes = this.findViewById(R.id.pantallaInformesBoton6Meses);
        this.botonYear = this.findViewById(R.id.pantallaInformesBotonYear);
        this.botonDia = this.findViewById(R.id.pantallaInformesBotonDia);
        this.textoMediaGanancia = this.findViewById(R.id.pantallaInformesTextoGanancia);
        this.textoMediaGasto = this.findViewById(R.id.pantallaInformesTextoGasto);
        this.textoMediaGananciaDefecto = this.textoMediaGanancia.getText().toString() + " ";
        this.textoMediaGastoDefecto = this.textoMediaGasto.getText().toString() + " ";
        this.botonVerMovimientos = this.findViewById(R.id.pantallaInformesBotonVerMovimientos);
        this.textoTotalMovimientos = this.findViewById(R.id.pantallaInformesTextoTotalMovimientos);
        this.listaMedias = new EnumMap<TipoMedia, BigDecimal>(TipoMedia.class);
        this.listaTotales = new EnumMap<TipoTotal, BigDecimal>(TipoTotal.class);
        this.listaTotalMovs = new EnumMap<TipoTotalMovs, Integer>(TipoTotalMovs.class);
        this.textoTotalGanancias = this.findViewById(R.id.pantallaInformesTextoTotalGanancia);
        this.textoTotalGastos = this.findViewById(R.id.pantallaInformesTextoTotalGastos);
        this.tiempoElegido = TipoTiempo.MES;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //this.fechaSeleccionada = LocalDateTime.now().minusDays(1); // Le quitamos un día porque por alguna razón, está devolviendo el día después;
            this.fechaSeleccionada = LocalDateTime.now();
        }
    }

}
