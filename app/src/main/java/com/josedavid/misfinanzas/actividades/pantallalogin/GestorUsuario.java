package com.josedavid.misfinanzas.actividades.pantallalogin;

import android.content.Context;

import com.josedavid.misfinanzas.bbdd.gestor.Gestor;
import com.josedavid.misfinanzas.bbdd.movimientos.Movimiento;
import com.josedavid.misfinanzas.bbdd.usuarios.Usuario;
import com.josedavid.misfinanzas.mensajes.Toaster;

import java.util.ArrayList;

public class GestorUsuario {
    private Gestor gestorBBDD;
    private Context context;
    private ArrayList<Usuario> listaUsuarios;
    private Toaster toaster;

    public ArrayList<Movimiento> obtenerListaMovimientos(Usuario usuario){
        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
        return listaMovimientos;
    }

    public Usuario obtenerUsuarioPorClave(int clave){
        Usuario usuario;
        usuario = gestorBBDD.obtenerUsuarioPorClave(clave);
        return usuario;
    }

    public boolean encontrarSiUsuarioExiste(String nombreUsuario){
        boolean encontrado = false;

        if (buscarUsuario(nombreUsuario) != null){
            encontrado = true;
        }

        return encontrado;

    }

    public Usuario buscarUsuario(String nombreUsuario){
        Usuario usuario = null;

        boolean encontrado = false;

        for (int i = 0;i<listaUsuarios.size() && !encontrado;i++){
            Usuario usuarioActual = listaUsuarios.get(i);
            if (usuarioActual.getNombre().equals(nombreUsuario)){
                usuario = usuarioActual;
                encontrado = true;
            }
        }

        return usuario;

    }

    public GestorUsuario(Context context, Toaster toaster){
        this.context = context;
        this.toaster = toaster;
        this.gestorBBDD = new Gestor(this.context);
        this.listaUsuarios = new ArrayList<>();
        obtenerUsuarios();

    }

    public void crearUsuario(String nombre, String password){
        boolean encontrado = false;

        encontrado = encontrarSiUsuarioExiste(nombre);

        if (!encontrado){
            if (!nombre.equals("") & !password.equals("")){
                gestorBBDD.insertarNuevoUsuario(nombre, password);
                obtenerUsuarios();
                toaster.mostrarToast(
                        "Usuario '" +  nombre + "' creado correctamente.",
                        0);
            } else {
                toaster.mostrarToast(
                        "Ni el nombre del usuario ni la contraseña deben estar en blanco.",
                        1);
            }
        } else {
            toaster.mostrarToast(
                    "Ya existe un usuario con este nombre.",
                    1);
        }

    }

    private void obtenerUsuarios(){
        this.listaUsuarios = gestorBBDD.obtenerUsuarios();
    }
}
