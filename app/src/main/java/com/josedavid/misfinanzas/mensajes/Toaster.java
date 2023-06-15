package com.josedavid.misfinanzas.mensajes;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    private Context context;

    public Toaster(Context context){
        this.context = context;

    }

    public void mostrarToast(String texto, int length){
        Toast.makeText(
                context,
                texto,
                length).show();
    }
}
