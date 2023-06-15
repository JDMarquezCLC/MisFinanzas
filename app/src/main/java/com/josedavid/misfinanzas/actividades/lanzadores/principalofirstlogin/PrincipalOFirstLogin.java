package com.josedavid.misfinanzas.actividades.lanzadores.principalofirstlogin;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.josedavid.misfinanzas.actividades.pantallafirstlog.PantallaFirstLogin;
import com.josedavid.misfinanzas.actividades.pantallainsertaractividad.PantallaInsertarMovimiento;
import com.josedavid.misfinanzas.actividades.pantallaprincipal.PantallaPrincipal;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;

public class PrincipalOFirstLogin {

    public Intent lanzarSegunSiEsFirstLoginONo(Usuario usuario, AppCompatActivity mainActivity){
        Intent actividad;
        if (usuario.isPrimerMovimiento()){
            actividad = new Intent(
                    mainActivity,
                    PantallaPrincipal.class);
        } else{
            actividad = new Intent(
                    mainActivity,
                    PantallaFirstLogin.class);
        }

        actividad.putExtra("usuario",usuario);

        return actividad;
    }

}
